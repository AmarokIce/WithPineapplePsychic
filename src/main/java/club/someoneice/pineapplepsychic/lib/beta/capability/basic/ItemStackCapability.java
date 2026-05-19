package club.someoneice.pineapplepsychic.lib.beta.capability.basic;

import club.someoneice.pineapplepsychic.lib.api.ICapability;
import club.someoneice.pineapplepsychic.lib.beta.capability.Capabilities;
import club.someoneice.pineapplepsychic.lib.inventory.SimpleInventory;
import com.google.common.annotations.Beta;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

@Beta
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
