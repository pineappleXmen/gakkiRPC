import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:Pineapple
 * @Description:
 * @Date: 19:04 2022/10/25
 * @Modifier by:
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    private byte messageType;

    private byte serializeType;

    private long requestId;

    private Object ObjectData;
}
