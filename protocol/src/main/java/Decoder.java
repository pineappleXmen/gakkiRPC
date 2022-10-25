import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.io.IOException;
import java.util.Arrays;

/**
 * @Author:Pineapple
 * @Description:
 * @Date: 19:23 2022/10/25
 * @Modifier by:
 */
public class Decoder extends LengthFieldBasedFrameDecoder {
    public Decoder() {
        super(Consts.MAX_FRAME_LENGTH,
                Consts.LENGTH_BEFORE_LENGTH_FILED,
                Consts.MESSAGE_LENGTH_FIELD_LENGTH,
                -(Consts.LENGTH_BEFORE_LENGTH_FILED + Consts.MESSAGE_LENGTH_FIELD_LENGTH),
                0);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        Object decoded = super.decode(ctx, in);
        if (decoded instanceof ByteBuf) {
            ByteBuf byteBuf = (ByteBuf) decoded;
            if (byteBuf.readableBytes() >= Consts.HEADER_LENGTH) {
                try {
                    decodeFrame(byteBuf);
                } catch (Exception e) {

                } finally {
                    byteBuf.release();
                }
            }
        }
        return decoded;
    }

    private Message decodeFrame(ByteBuf byteBuf) throws IOException, ClassNotFoundException {
        checkMagicNum(byteBuf);
        int magicNum = byteBuf.readInt();
        byte version = byteBuf.readByte();
        byte serializerType = byteBuf.readByte();
        byte MessageType = byteBuf.readByte();
        int seqID = byteBuf.readInt();
        int len = byteBuf.readInt();
        byte[] in = new byte[len];
        byteBuf.readBytes(in,0,len);
        Message msg = new Message();
        if(serializerType==0) {
            ProtoStuffSerializer serializer = new ProtoStuffSerializer();
            msg = serializer.deserialize(in, Message.class);
        }else if(serializerType ==1) {
            FastjsonSerializer serializer = new FastjsonSerializer();
            msg = serializer.deserialize(in, Message.class);
        }
        return msg;
    }

    private void checkMagicNum(ByteBuf in){
        byte[] bytes = new byte[Consts.MAGIC_NUM_LENGTH];
        in.readBytes(bytes);
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i]!=Consts.MAGIC[i]){
                throw new IllegalStateException(("Wrong Magic Number")+ Arrays.toString(bytes));
            }
        }
    }

}

