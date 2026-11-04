package club.someoneice.pineapplepsychic.feature.function;

import com.google.common.base.Supplier;
import java.util.Objects;

/**
 * Lazy is a derivative structure of Supplier. <br>
 * Lazy also has the feature of lazy evaluation, only computing its value when necessary instead of
 * using the data in advance. <br>
 * Besides being able to access the data it holds, it can also be used to set data or clear the
 * pointer. Lazy is used to hold temporary but memory-heavy final type data, like data structures
 * such as {@link com.google.common.collect.ImmutableMap ImmutableMap}.
 *
 * @see club.someoneice.pineapplepsychic.common.lazy.Lazy Lazy
 * @see club.someoneice.pineapplepsychic.common.lazy.InstanceLazy InstanceLazy
 */
public interface ILazy<T> extends Supplier<T> {
  @Override
  T get();

  void set(T value);

  default void clean() {
    this.set(null);
  }

  default boolean ready() {
    return Objects.nonNull(this.get());
  }

  default java.util.function.Supplier<T> asJavaSupplier() {
    return this::get;
  }
}
