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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseMessage<T> {
    //请求的id 用于从表中找到future
    private long requestId;

    //消息状态相应码
    private Integer status;

    //响应消息对象
    private T data;

    public static <T> ResponseMessage<T> success(T data,long requestId){
        return ResponseMessage.<T>builder()
                .status(200)
                .data(data)
                .build();
    }

    public static <T> ResponseMessage<T> failed(){
        return ResponseMessage.<T>builder()
                .status(404)
                .build();
    }
}
