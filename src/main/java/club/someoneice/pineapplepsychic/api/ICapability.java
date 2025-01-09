package club.someoneice.pineapplepsychic.api;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public interface ICapability extends Cloneable {
    void saveToNBT(NBTTagCompound tag);
    void loadFromNBT(NBTTagCompound tag);
    ResourceLocation getCapabilityKey();
}
