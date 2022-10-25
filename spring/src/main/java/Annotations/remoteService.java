package Annotations;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

/**
 * @Author:Pineapple
 * @Description:
 * @Date: 19:42 2022/10/25
 * @Modifier by:
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface remoteService {

    @NotNull

    String serviceName();

    String version() default "";
}
