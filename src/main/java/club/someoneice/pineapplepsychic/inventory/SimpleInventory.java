package club.someoneice.pineapplepsychic.inventory;

import club.someoneice.cookie.util.ObjectUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

public class SimpleInventory implements IInventory {
    private final String name;
    private final int size;
    @Nullable
    private final TileEntity tile;
    private final ItemStack[] inventory;

    public SimpleInventory(String name, int size, @Nullable TileEntity tile) {
        this.name = name;
        this.size = size;
        this.tile = tile;
        this.inventory = new ItemStack[size];
    }

    public SimpleInventory(int size, TileEntity tile) {
        this("", size, tile);
    }

    public SimpleInventory(int size) {
        this("", size, null);
    }

    public static SimpleInventory createAndLoadFromNBT(NBTTagCompound nbt) {
        SimpleInventory simpleInventory = new SimpleInventory(nbt.getInteger("inv_size"));
        simpleInventory.load(nbt);
        return simpleInventory;
    }

    public void load(NBTTagCompound nbt) {
        for (int i = 0; i < this.size; i++) {
            if (nbt.hasKey(Integer.toString(i))) {
                this.inventory[i] = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag(Integer.toString(i)));
            }
        }
    }

    public NBTTagCompound write() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("inv_size", this.size);
        for (int i = 0; i < this.size; i++) {
            if (this.inventory[i] != null) {
                nbt.setTag(Integer.toString(i), this.inventory[i].writeToNBT(new NBTTagCompound()));
            }
        }

        return nbt;
    }

    @Override
    public int getSizeInventory() {
        return this.size;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot >= this.size) {
            return null;
        }
        return this.inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int size) {
        if (slot >= this.size) {
            return null;
        }
        ItemStack item = this.inventory[slot];
        ItemStack out = item.copy();
        if (item.stackSize <= size) {
            this.inventory[slot] = null;
            return out;
        }

        item.stackSize -= size;
        out.stackSize = size;
        return out;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        if (slot >= this.size) {
            return null;
        }
        return this.inventory[slot];
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack item) {
        if (slot >= this.size) {
            return;
        }
        this.inventory[slot] = item;
    }

    @Override
    public String getInventoryName() {
        return this.name;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
        ObjectUtil.objectLet(this.tile, TileEntity::markDirty);
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    public void checkAndCleanNullData() {
        for (int i = 0; i < this.inventory.length; i++) {
            ItemStack item = this.inventory[i];
            if (item == null) return;
            if (item.stackSize <= 0) this.inventory[i] = null;
        }
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return true;
    }
}
