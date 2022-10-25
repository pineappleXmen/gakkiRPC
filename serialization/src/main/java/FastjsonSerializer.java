import com.alibaba.fastjson.JSON;

import java.io.IOException;

/**
 * @Author:Pineapple
 * @Description:
 * @Date: 19:21 2022/10/25
 * @Modifier by:
 */
public class FastjsonSerializer implements Serializer{
    @Override
    public byte[] serialize(Object obj) throws IOException {
        return JSON.toJSONBytes(obj);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException, ClassNotFoundException {
        T t = JSON.parseObject(bytes, clazz);
        return t;
    }
}
