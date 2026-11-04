package club.someoneice.pineapplepsychic.common.tile;

import club.someoneice.pineapplepsychic.feature.inventory.SimpleInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

public abstract class TileEntityWithSidedInventory extends TileEntityBase
    implements ISidedInventory {
  protected SimpleInventory simpleInventory;

  protected TileEntityWithSidedInventory(int size) {
    this.simpleInventory = new SimpleInventory(size, this);
  }

  @Override
  public int getSizeInventory() {
    return this.simpleInventory.getSizeInventory();
  }

  @Override
  public ItemStack getStackInSlot(int slot) {
    return this.simpleInventory.getStackInSlot(slot);
  }

  @Override
  public ItemStack decrStackSize(int slot, int size) {
    return this.simpleInventory.decrStackSize(slot, size);
  }

  @Override
  public ItemStack getStackInSlotOnClosing(int slot) {
    return this.simpleInventory.getStackInSlotOnClosing(slot);
  }

  @Override
  public void setInventorySlotContents(int slot, ItemStack item) {
    this.simpleInventory.setInventorySlotContents(slot, item);
  }

  @Override
  public String getInventoryName() {
    return this.simpleInventory.getInventoryName();
  }

  @Override
  public boolean hasCustomInventoryName() {
    return this.simpleInventory.hasCustomInventoryName();
  }

  @Override
  public int getInventoryStackLimit() {
    return this.simpleInventory.getInventoryStackLimit();
  }

  @Override
  public boolean isUseableByPlayer(EntityPlayer player) {
    return this.simpleInventory.isUseableByPlayer(player);
  }

  @Override
  public void openInventory() {
    this.simpleInventory.openInventory();
  }

  @Override
  public void closeInventory() {
    this.simpleInventory.closeInventory();
  }

  @Override
  public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
    return this.simpleInventory.isItemValidForSlot(slot, itemStack);
  }
}
