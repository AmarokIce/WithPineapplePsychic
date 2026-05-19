package club.someoneice.pineapplepsychic.lib.bean;

import club.someoneice.pineapplepsychic.lib.api.ILazy;
import com.google.common.base.Supplier;

import javax.annotation.Nonnull;
import java.util.Objects;

public class NonnullLazy<T> implements ILazy<T> {
  private final Supplier<T> defaultValue;
  private T value;

  public NonnullLazy(@Nonnull Supplier<T> defaultValue) {
    this.defaultValue = defaultValue;
  }

  public NonnullLazy(@Nonnull Supplier<T> defaultValue, T value) {
    this.defaultValue = defaultValue;
    this.value = value;
  }

  @Nonnull
  @Override
  public T get() {
    return Objects.isNull(this.value) ? (this.value = defaultValue.get()) : this.value;
  }

  @Override
  public void set(@Nonnull T value) {
    Objects.requireNonNull(value);
    this.value = value;
  }

  @Override
  public void clean() {
    this.set(this.defaultValue.get());
  }

  @Override
  public boolean ready() {
    return true;
  }
}
