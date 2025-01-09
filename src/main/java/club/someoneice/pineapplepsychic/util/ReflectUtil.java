package club.someoneice.pineapplepsychic.util;

import java.lang.reflect.Field;

public final class ReflectUtil {
    private ReflectUtil() {
    }

    public static <T> T reflectField(Object clazz, String fieldName) throws IllegalAccessException, NoSuchFieldException {
        Field f = clazz.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        return (T) f.get(clazz);
    }

    public static <T> T clone(T obj) throws CloneNotSupportedException {
        if (obj == null) {
            throw new NullPointerException();
        }

        Class<T> objClazz = (Class<T>) obj.getClass();
        T objectClone;
        try {
            objectClone = (T) objClazz.newInstance();
        } catch (Exception e) {
            throw new CloneNotSupportedException("Clone failed: the object must had default constructor!");
        }

        try {
            for (Field field : objClazz.getDeclaredFields()) {
                field.setAccessible(true);
                field.set(objectClone, field.get(obj));
            }
        } catch (Exception e) {
            throw new CloneNotSupportedException("Clone failed: cannot clone object!");
        }

        return objectClone;
    }
}
