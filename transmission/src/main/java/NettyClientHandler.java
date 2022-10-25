import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author:Pineapple
 * @Description:
 * @Date: 19:29 2022/10/25
 * @Modifier by:
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<Message> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        ResponseMessage<?> objectData = (ResponseMessage<?>) message.getObjectData();
        PromiseProcesses.completed(objectData);
    }


}
