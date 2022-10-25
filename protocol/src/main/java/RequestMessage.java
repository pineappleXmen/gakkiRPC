import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:Pineapple
 * @Description:
 * @Date: 19:11 2022/10/25
 * @Modifier by:
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestMessage {
    //需要调用的方法的接口名
    private String interfaceName;

    //需要调用的方法名
    private String methodName;

    //需要调用的参数列表
    private Object[] params;

    //参数类型
    private Class<?>[] paramTypes;

    //版本号
    private String version;
}
