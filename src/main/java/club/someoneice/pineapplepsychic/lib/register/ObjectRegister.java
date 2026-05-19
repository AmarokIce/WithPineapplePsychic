package club.someoneice.pineapplepsychic.lib.register;

import club.someoneice.pineapplepsychic.lib.api.ILazy;
import club.someoneice.pineapplepsychic.lib.bean.InstanceLazy;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.util.ResourceLocation;

import javax.annotation.CheckForNull;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

public class ObjectRegister<T> {
  public final String modid;
  private final DataRegister<T> dataRegister;

  private ImmutableMap<ResourceLocation, T> frozen;
  private ILazy<Map<ResourceLocation, T>> register = new InstanceLazy<>(Maps.newConcurrentMap());

  public ObjectRegister(final String modid, final DataRegister<T> register) {
    this.modid = modid;
    this.dataRegister = register;
  }

  public T register(String name, T value) {
    this.register.get().put(new ResourceLocation(modid, name), value);
    return value;
  }

  @CheckForNull
  public T get(ResourceLocation name) {
    return (Objects.isNull(this.frozen) ? this.register.get() : this.frozen).get(name);
  }

  @CheckForNull
  public T get(String name) {
    return this.get(new ResourceLocation(modid, name));
  }

  public ImmutableMap<ResourceLocation, T> getAll() {
    return ImmutableMap.copyOf(Objects.isNull(this.frozen) ? this.register.get() : this.frozen);
  }

  public void frozen(BiConsumer<ResourceLocation, T> func) {
    frozen = ImmutableMap.copyOf(register.get());
    register.clean();
    frozen.forEach(func);

    this.dataRegister.register(this);
  }
}
