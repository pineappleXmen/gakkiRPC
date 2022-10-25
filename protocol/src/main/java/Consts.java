/**
 * @Author:Pineapple
 * @Description:
 * @Date: 19:04 2022/10/25
 * @Modifier by:
 */
public class Consts {
    public static final int  MAX_FRAME_LENGTH = 8*1024*1024;

    public static final byte[] MAGIC = new byte[]{'5','2','9','6'};
    public static final int  MAGIC_NUM_LENGTH = 4;
    public static final int  VERSION_LENGTH = 1;
    public static final int  SERIALIZATION_TYPE_LENGTH = 1;
    public static final int  OP_LENGTH = 1;
    public static final int  SEQUENCE_NUM_LENGTH = 4;
    public static final int  MESSAGE_LENGTH_FIELD_LENGTH = 4;
    public static final int  HEADER_LENGTH = MAGIC_NUM_LENGTH+VERSION_LENGTH+SERIALIZATION_TYPE_LENGTH+OP_LENGTH+SEQUENCE_NUM_LENGTH
            +MESSAGE_LENGTH_FIELD_LENGTH;
    public static final int LENGTH_BEFORE_LENGTH_FILED = MAGIC_NUM_LENGTH+VERSION_LENGTH+SERIALIZATION_TYPE_LENGTH+OP_LENGTH+SEQUENCE_NUM_LENGTH;

}
