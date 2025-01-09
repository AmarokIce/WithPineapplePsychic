package club.someoneice.pineapplepsychic.capability;

import club.someoneice.cookie.util.ObjectUtil;
import club.someoneice.pineapplepsychic.PineappleMain;
import club.someoneice.pineapplepsychic.api.ICapability;
import club.someoneice.pineapplepsychic.util.ReflectUtil;
import com.google.common.collect.Maps;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import java.util.Map;
import java.util.Objects;

public final class Capabilities {
    public static final Capabilities INSTANCE = new Capabilities();

    public static Capabilities getCapabilities() {
        return INSTANCE;
    }

    private final Map<ResourceLocation, ICapability> capabilities;

    public static final ResourceLocation ITEMSTACK_CAPABILITY = new ResourceLocation(PineappleMain.MODID, "itemstack");

    private Capabilities() {
        this.capabilities = Maps.newLinkedHashMap();
    }

    /**
     * 提取原始 ICapability.
     * @param location 註冊名
     * @return 註冊的原始 ICapabiltiy.
     */
    private ICapability getCapability(ResourceLocation location) {
        return this.capabilities.get(location);
    }

    /**
     * 提取 ICapability.
     * @param location 註冊名
     * @return 獲取拷貝的 ICapabiltiy.
     */
    public ICapability copyCapability(ResourceLocation location) {
        try {
            return ReflectUtil.clone(getCapability(location));
        } catch (CloneNotSupportedException e) {
            PineappleMain.LOGGER.error(e);
            return null;
        }
    }

    /**
     * 通過克隆加載數據。由於對象限制，加載後需要重新提交。
     * 請使用更安全的 {@link Capabilities#getCapability(Entity, ResourceLocation, ObjectUtil.CallBackWithType)}
     *
     * @param living 被提取對象
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

    private void post(Entity living, ICapability capability) {
        ResourceLocation name = capability.getCapabilityKey();
        // TODO
    }
}
