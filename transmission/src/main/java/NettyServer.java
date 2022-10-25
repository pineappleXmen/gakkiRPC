import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;

/**
 * @Author:Pineapple
 * @Description:
 * @Date: 19:32 2022/10/25
 * @Modifier by:
 */
public class NettyServer {
    public void start() throws InterruptedException {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        DefaultEventExecutorGroup handlers = new DefaultEventExecutorGroup(
                Runtime.getRuntime().availableProcessors()*2
        );
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(boss,worker)
                .channel(Epoll.isAvailable()? EpollServerSocketChannel.class: NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline p = socketChannel.pipeline();
                        p.addLast(new Encoder());
                        p.addLast(new Decoder());
                        p.addLast(handlers,new NettyServerHandler());
                    }
                });

        //绑定端口号
        ChannelFuture future = bootstrap.bind("127.0.0.1", 8889).sync();

        //等待将来的关闭事件
        future.channel().closeFuture().sync();
    }
}
