import java.io.IOException;

/**
 * @Author:Pineapple
 * @Description:
 * @Date: 19:18 2022/10/25
 * @Modifier by:
 */
public interface Serializer {
    public byte[] serialize(Object obj) throws IOException;
    public <T> T  deserialize(byte[] bytes,Class<T> clazz) throws IOException, ClassNotFoundException;
}
