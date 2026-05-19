package club.someoneice.pineapplepsychic.lib.api;

import com.google.common.base.Supplier;

import java.util.Objects;

public interface ILazy<T> extends Supplier<T> {
  T get();
  void set(T value);

  default void clean() {
    this.set(null);
  }

  default boolean ready() {
    return Objects.nonNull(this.get());
  }
}
