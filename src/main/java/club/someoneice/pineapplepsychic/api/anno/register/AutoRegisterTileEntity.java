package club.someoneice.pineapplepsychic.api.anno.register;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AutoRegisterTileEntity {
  String name() default "";
}
