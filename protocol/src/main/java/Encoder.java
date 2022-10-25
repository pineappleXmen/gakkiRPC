import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author:Pineapple
 * @Description:
 * @Date: 19:22 2022/10/25
 * @Modifier by:
 */
public class Encoder extends MessageToByteEncoder<Message> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, ByteBuf byteBuf) throws Exception {
        //4字节 魔数
        byteBuf.writeBytes(new byte[]{5,2,9,6});

        //1字节 版本
        byteBuf.writeByte(1);

        //1字节    序列化方式  0 -> protostuff 1 -> fastjson 2 -> jdk
        byteBuf.writeByte(0);

        //1字节   指令类型
        byteBuf.writeByte(0);

        //4字节 序列号
        byteBuf.writeInt(1);

        ProtoStuffSerializer serializer = new ProtoStuffSerializer();

        byte[] bytes = serializer.serialize(message);

        //4字节 需要传输的字节数组的长度
        byteBuf.writeInt(bytes.length);

        byteBuf.writeBytes(bytes);
    }
}
