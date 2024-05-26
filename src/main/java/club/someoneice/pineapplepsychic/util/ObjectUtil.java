package club.someoneice.pineapplepsychic.util;

@SuppressWarnings("unused")
public final class ObjectUtil {
    private ObjectUtil() {}

    public static <T> T objectLet(T obj, CallBackWithType<T> c) {
        if (checkNonNull(obj)) c.run(obj);
        return obj;
    }

    public static <T> T objectRun(T obj, CallBackWithReturnType<T> c) {
        return c.run(obj);
    }

    public static <T, R> R objectDo(T obj, CallBackWithReturnAny<T, R> c) {
        return c.run(obj);
    }

    public static <T> void objectLambda(T obj, CallBackWithType<T> c) {
        c.run(obj);
    }

    public static boolean checkNonNull(Object obj) {
        return obj != null;
    }


    public interface CallBackWithType<T> {
        void run(T t);
    }

    public interface CallBackWithReturnAny<T, R> {
        R run(T t);
    }

    public interface CallBackWithReturnType<T> {
        T run(T t);
    }
}
