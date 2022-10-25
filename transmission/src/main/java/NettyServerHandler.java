import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;

/**
 * @Author:Pineapple
 * @Description:
 * @Date: 19:33 2022/10/25
 * @Modifier by:
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<Message> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message request) throws Exception {

        Message.MessageBuilder response = Message.builder()
                .requestId(request.getRequestId());

        RequestMessage messageRequest = (RequestMessage) request.getObjectData();
        Object result;
        try {
            Object service = ServiceCaches.getService(messageRequest.getInterfaceName());
            Method method = service.getClass().getMethod(messageRequest.getMethodName(), messageRequest.getParamTypes());
            result = method.invoke(service, messageRequest.getParams());
        }catch (Exception e){
            throw new Exception(e.getMessage(),e);
        }

        if (channelHandlerContext.channel().isActive()&&channelHandlerContext.channel().isWritable()){
            ResponseMessage<Object> success = ResponseMessage.success(result, request.getRequestId());
            response.build().setObjectData(success);
        }else {
            ResponseMessage<Object> failed = ResponseMessage.failed();
            response.build().setObjectData(failed);
        }
        channelHandlerContext.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
    }
}
