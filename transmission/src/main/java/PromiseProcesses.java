import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author:Pineapple
 * @Description:
 * @Date: 19:30 2022/10/25
 * @Modifier by:
 */
public class PromiseProcesses {
    private static final Map<Long, CompletableFuture<ResponseMessage<?>>> UnCompletedProcesses = new ConcurrentHashMap<>();

    public static void addFutureProcess(Long requestId,CompletableFuture<ResponseMessage<?>> future){
        UnCompletedProcesses.put(requestId,future);
    }

    public static void completed(ResponseMessage<?> response){
        CompletableFuture<ResponseMessage<?>> future = UnCompletedProcesses.remove(response.getRequestId());
        if (future!=null){
            future.complete(response);
        }else {
            throw new IllegalStateException("response is null");
        }
    }
}
