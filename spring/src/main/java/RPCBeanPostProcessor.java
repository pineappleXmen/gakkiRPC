import Annotations.RPCService;
import Annotations.remoteService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;

/**
 * @Author:Pineapple
 * @Description:
 * @Date: 19:35 2022/10/25
 * @Modifier by:
 */
public class RPCBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        RPCService service = bean.getClass().getAnnotation(RPCService.class);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Field[] declaredField = bean.getClass().getDeclaredFields();
        for(Field field:declaredField){
            remoteService requestedService = field.getAnnotation(remoteService.class);
            if (requestedService!=null){
                ClientProxy clientProxy = new ClientProxy();
                Object proxy = clientProxy.getProxy(field.getType());
                field.setAccessible(true);
                try {
                    field.set(bean,proxy);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }
}
