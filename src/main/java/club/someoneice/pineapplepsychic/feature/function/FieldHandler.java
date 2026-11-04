package club.someoneice.pineapplepsychic.feature.function;

import java.lang.reflect.Field;
import javax.annotation.CheckForNull;

@FunctionalInterface
public interface FieldHandler<C, R> {
  /**
   * Set or get the value from field.
   *
   * @param field The field from class C.
   * @param object Nullable if the field is static. Don't send any Instance Object when type are
   *     Void.
   * @param root The collection root. Like: NBTTagCompound, MapNode, ArrayNode...
   * @param sign The sign value of variable in NBTTagCompound, if it's blank or null, take the field
   *     value.
   * @return True if anything all in OK.
   */
  boolean handle(final Field field, @CheckForNull C object, R root, final String sign);
}
