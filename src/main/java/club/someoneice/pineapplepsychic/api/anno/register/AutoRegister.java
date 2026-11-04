package club.someoneice.pineapplepsychic.api.anno.register;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AutoRegister {
  String value() default "";
  String displayName() default "";
  String textureName() default "";
  String registerName() default "";
}
