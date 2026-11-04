package club.someoneice.pineapplepsychic.common.lazy;

import club.someoneice.pineapplepsychic.feature.function.ILazy;
import com.google.common.base.Supplier;
import java.util.Objects;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public class Lazy<T> implements ILazy<T> {
  @Nonnull private final Supplier<T> value;
  private T obj;

  public Lazy(@Nonnull Supplier<T> value) {
    this.value = value;
    Objects.requireNonNull(value);
  }

  @Override
  @CheckForNull
  public T get() {
    return Objects.isNull(obj) ? (obj = value.get()) : obj;
  }

  @Override
  public void set(T value) {
    this.obj = value;
  }
}
