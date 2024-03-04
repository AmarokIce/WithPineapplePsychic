package club.someoneice.pineapplepsychic.util;

import java.lang.reflect.Field;

public class ReflectUtil {
    public static <T> T reflectField(Object clazz, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field f = clazz.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        return (T) f.get(clazz);
    }
}
