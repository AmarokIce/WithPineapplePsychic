package club.someoneice.pineapplepsychic.util;

import cpw.mods.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;

/**
 * @deprecated Use forge's {@link ReflectionHelper}.
 */
@Deprecated
public final class ReflectUtil {
    private ReflectUtil() {}

    @Deprecated
    public static <T> T reflectField(Object clazz, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field f = clazz.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        return (T) f.get(clazz);
    }
}
