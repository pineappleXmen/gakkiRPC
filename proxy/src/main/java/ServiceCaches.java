import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author:Pineapple
 * @Description:
 * @Date: 19:43 2022/10/25
 * @Modifier by:
 */
public class ServiceCaches {
    private static final Map<String,Object> SERVICES_MAP = new ConcurrentHashMap<>();
    public static void addService(String serviceName,String version,Object service){
        Class<?>[] interfaces = service.getClass().getInterfaces();
        if (interfaces.length==0){
            throw new IllegalStateException("error occurred,service has not implement any interfaces");
        }
        SERVICES_MAP.putIfAbsent(serviceName,service);
    }
    public static Object getService(String serviceName){
        Object service = SERVICES_MAP.get(serviceName);
        if (service==null) throw new IllegalStateException("service not found");
        return service;
    }
}
