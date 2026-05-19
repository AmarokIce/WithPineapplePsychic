package club.someoneice.pineapplepsychic.lib.register;

import com.google.common.collect.Lists;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

public class DataRegister<T> {
  private final List<ObjectRegister<T>> registers = Lists.newArrayList();
  private final BiConsumer<ResourceLocation, T> func;

  public DataRegister(BiConsumer<ResourceLocation, T> func) {
    this.func = func;
  }

  @Nullable
  public T get(ResourceLocation location) {
    final ObjectRegister<T> register = this.registers.stream()
      .filter(it -> Objects.equals(it.modid, location.getResourceDomain()))
      .findFirst()
      .orElse(null);

    return Objects.nonNull(register)
      ? register.get(location)
      : null;
  }

  public void register(ObjectRegister<T> register) {
    register.getAll().forEach(this.func);
  }
}
