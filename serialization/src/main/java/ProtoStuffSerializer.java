import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.io.IOException;

/**
 * @Author:Pineapple
 * @Description:
 * @Date: 19:18 2022/10/25
 * @Modifier by:
 */
public class ProtoStuffSerializer implements Serializer{
    private static final LinkedBuffer BUFFER = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
    @Override
    public byte[] serialize(Object obj) throws IOException {
        Schema schema = RuntimeSchema.getSchema(obj.getClass());
        try {
            return ProtostuffIOUtil.toByteArray(obj,schema,BUFFER);
        }finally {
            BUFFER.clear();
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException, ClassNotFoundException {
        Schema schema = RuntimeSchema.getSchema(clazz.getClass());
        T obj = (T) schema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes,obj,schema);
        return obj;
    }
}
