package club.someoneice.pineapplepsychic.lib.bean;

import club.someoneice.pineapplepsychic.lib.api.ILazy;
import com.google.common.base.Supplier;

import javax.annotation.CheckForNull;
import java.util.Objects;

public class Lazy<T> implements ILazy<T> {
  private final Supplier<T> value;
  private T obj;

  public Lazy(Supplier<T> value) {
    this.value = value;
  }

  @CheckForNull
  @Override
  public T get() {
    return Objects.isNull(obj) ? (obj = value.get()) : obj;
  }

  @Override
  public void set(T value) {
    this.obj = value;
  }
}
