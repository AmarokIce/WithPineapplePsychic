package club.someoneice.pineapplepsychic.feature;

import java.util.Objects;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class ObjectLambda {
  private ObjectLambda() {}

  /** Do something with non-null object, or returns null if object was null. */
  @CheckForNull
  public static <T, R> R objectLet(
      @Nullable final T obj, @Nonnull final CallbackWithReturnAny<T, R> c) {
    if (Objects.nonNull(obj)) {
      return c.run(obj);
    }
    return null;
  }

  /** Do something with nullable, and return self. */
  @Nullable
  public static <T> T objectRun(@Nullable final T obj, @Nonnull final Callback<T> c) {
    c.run(obj);
    return obj;
  }

  /**
   * Do something with nullable, and return a new value.
   *
   * @see objectLet
   */
  @Nullable
  public static <T, R> R objectDo(
      @Nullable final T obj, @Nonnull final CallbackWithReturnAny<T, R> c) {
    return c.run(obj);
  }

  /** Apply function. */
  public static <T> void objectLambda(@Nullable final T obj, @Nonnull final Callback<T> c) {
    c.run(obj);
  }

  public static <T> void objectLambdaNonnull(@Nullable final T obj, @Nonnull final Callback<T> c) {
    if (Objects.nonNull(obj)) {
      c.run(obj);
    }
  }

  public interface Callback<T> {
    void run(T t);
  }

  public interface CallbackWithReturnAny<T, R> {
    R run(T t);
  }
}
