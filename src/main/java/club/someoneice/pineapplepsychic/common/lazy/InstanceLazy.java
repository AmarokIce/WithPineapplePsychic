package club.someoneice.pineapplepsychic.common.lazy;

import club.someoneice.pineapplepsychic.feature.function.ILazy;
import javax.annotation.CheckForNull;

/**
 * InstanceLazy isn't actually lazy, but it can be used to hold those fixed data structures and
 * release them. InstanceLazy never becomes a magic member.
 */
public class InstanceLazy<T> implements ILazy<T> {
  private T value = null;

  public InstanceLazy() {}

  public InstanceLazy(T value) {
    this.value = value;
  }

  @CheckForNull
  @Override
  public T get() {
    return value;
  }

  @Override
  public void set(T value) {
    this.value = value;
  }
}
