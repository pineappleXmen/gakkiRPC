import java.io.*;

/**
 * @Author:Pineapple
 * @Description:
 * @Date: 19:21 2022/10/25
 * @Modifier by:
 */
public class JdkSerializer implements Serializer{
    @Override
    public byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        return bos.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
        T t = (T)ois.readObject();
        return t;
    }
}
