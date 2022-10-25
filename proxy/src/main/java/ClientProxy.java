import Annotations.remoteService;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author:Pineapple
 * @Description:
 * @Date: 19:43 2022/10/25
 * @Modifier by:
 */
public class ClientProxy implements InvocationHandler {


    private remoteService requestService;




    public <T> T getProxy(Class<T> clazz){
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),new Class<?>[]{clazz},this);
    }



    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RequestMessage request = RequestMessage.builder()

                .interfaceName(method.getDeclaringClass().getCanonicalName())
                .methodName(method.getName())
                .params(args)
                .paramTypes(method.getParameterTypes())
                .methodName(requestService.serviceName())
                .version(requestService.version())
                .build();
        return new Object();
    }
}