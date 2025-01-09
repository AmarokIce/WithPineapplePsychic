package club.someoneice.pineapplepsychic.capability.basic;

import club.someoneice.pineapplepsychic.api.ICapability;
import club.someoneice.pineapplepsychic.capability.Capabilities;
import club.someoneice.pineapplepsychic.inventory.SimpleInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class ItemStackCapability implements ICapability {
    private final SimpleInventory inventory;

    public ItemStackCapability(SimpleInventory inventory) {
        this.inventory = inventory;
    }

    public ItemStackCapability(int size) {
        this.inventory = new SimpleInventory(size);
    }

    public ItemStackCapability() {
        this.inventory = new SimpleInventory(0);
    }

    @Override
    public void saveToNBT(NBTTagCompound tag) {
        tag.setTag("inventory", this.inventory.write());
    }

    @Override
    public void loadFromNBT(NBTTagCompound tag) {
        if (tag.hasKey("inventory")) {
            this.inventory.load(tag.getCompoundTag("inventory"));
        }
    }

    @Override
    public ResourceLocation getCapabilityKey() {
        return Capabilities.ITEMSTACK_CAPABILITY;
    }
}
