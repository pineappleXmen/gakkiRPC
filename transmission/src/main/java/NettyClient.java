import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @Author:Pineapple
 * @Description:
 * @Date: 19:26 2022/10/25
 * @Modifier by:
 */
public class NettyClient {
    private final Bootstrap bootstrap;

    private static NettyClient instance = null;
    private static final Map<SocketAddress,Channel> CHANNEL_MAP  = new ConcurrentHashMap<>();

    public static NettyClient getInstance(){
        if(instance==null){
            synchronized (NettyClient.class){
                if (instance==null){
                    instance = new NettyClient();
                }
            }
        }
        return instance;
    }

    NettyClient(){
        bootstrap = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(Epoll.isAvailable()? EpollSocketChannel.class:NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        ChannelPipeline p = sc.pipeline();
                        p.addLast(new Encoder());
                        p.addLast(new Decoder());
                        p.addLast(new NettyClientHandler());
                    }
                });
    }

    public Channel getChannel(SocketAddress addr){
        Channel channel = CHANNEL_MAP.get(addr);
        if (channel==null||!channel.isActive()){
            channel.connect(addr);
            CHANNEL_MAP.put(addr,channel);
        }
        return channel;
    }


    private Channel connect(SocketAddress addr) throws Exception {
        try {
            CompletableFuture<Channel> completableFuture = new CompletableFuture<>();
            ChannelFuture connect = bootstrap.connect(addr);
            connect.addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    completableFuture.complete(future.channel());
                } else {
                    throw new IllegalStateException(("connect to" + addr + " failed"));
                }
            });
            return completableFuture.get(5, TimeUnit.SECONDS);
        }catch (Exception e){
            throw new Exception("connect failed");
        }
    }
}
