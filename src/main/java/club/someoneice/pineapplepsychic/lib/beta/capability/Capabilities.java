package club.someoneice.pineapplepsychic.lib.beta.capability;

import club.someoneice.cookie.util.ObjectUtil;
import club.someoneice.pineapplepsychic.PineappleMain;
import club.someoneice.pineapplepsychic.lib.api.ICapability;
import club.someoneice.pineapplepsychic.lib.util.UtilReflect;
import com.google.common.annotations.Beta;
import com.google.common.collect.Maps;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import java.util.Map;
import java.util.Objects;

@Beta
public final class Capabilities {
  public static final Capabilities INSTANCE = new Capabilities();
  public static final ResourceLocation ITEMSTACK_CAPABILITY = new ResourceLocation(PineappleMain.MODID, "itemstack");
  private final Map<ResourceLocation, ICapability> capabilities;

  private Capabilities() {
    this.capabilities = Maps.newLinkedHashMap();
  }

  public static Capabilities getCapabilities() {
    return INSTANCE;
  }

  /**
   * 通過克隆加載數據。由於對象限制，加載後需要重新提交。
   * 請使用更安全的 {@link Capabilities#getCapability(Entity, ResourceLocation, ObjectUtil.CallBackWithType)}
   *
   * @param living           被提取對象
   * @param resourceLocation 註冊名
   * @return 真實數據
   */
  public static ICapability getCapability(Entity living, ResourceLocation resourceLocation) {
    ICapability capability = INSTANCE.copyCapability(resourceLocation);
    if (Objects.isNull(capability)) {
      return null;
    }

    NBTTagCompound nbt = living.getEntityData().getCompoundTag(resourceLocation.toString());
    capability.loadFromNBT(nbt);

    return capability;
  }

  public static void getCapability(Entity living, ResourceLocation resourceLocation, ObjectUtil.CallBackWithType<ICapability> function) {
    ICapability capability = getCapability(living, resourceLocation);
    if (Objects.isNull(capability)) {
      return;
    }

    function.run(capability);

    NBTTagCompound nbt = new NBTTagCompound();
    capability.saveToNBT(nbt);
    living.getEntityData().setTag(resourceLocation.toString(), nbt);
    Capabilities.getCapabilities().post(living, capability);
  }

  /**
   * 提取原始 ICapability.
   *
   * @param location 註冊名
   * @return 註冊的原始 ICapabiltiy.
   */
  private ICapability getCapability(ResourceLocation location) {
    return this.capabilities.get(location);
  }

  /**
   * 提取 ICapability.
   *
   * @param location 註冊名
   * @return 獲取拷貝的 ICapabiltiy.
   */
  public ICapability copyCapability(ResourceLocation location) {
    try {
      return UtilReflect.clone(getCapability(location));
    } catch (CloneNotSupportedException e) {
      PineappleMain.LOGGER.error(e);
      return null;
    }
  }

  private void post(Entity living, ICapability capability) {
    ResourceLocation name = capability.getCapabilityKey();
    if (!living.worldObj.isRemote) {

    }
  }
}
