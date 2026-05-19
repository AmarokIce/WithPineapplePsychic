package club.someoneice.pineapplepsychic.lib.bean;

import club.someoneice.pineapplepsychic.lib.api.ILazy;

import javax.annotation.CheckForNull;

public class InstanceLazy<T> implements ILazy<T> {
  private T value = null;

  public InstanceLazy() {
  }

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
