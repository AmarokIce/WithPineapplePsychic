package club.someoneice.pineapplepsychic.util;

import com.google.gson.Gson;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class CompoundList {
    private final ItemStack[] itemList;

    public CompoundList(int i) {
        this.itemList = new ItemStack[i];
        initList();
    }

    public CompoundList(List<ItemStack> list) {
        this.itemList = new ItemStack[list.size()];
        for (int o = 0; o < list.size(); o++) itemList[o] = list.get(o);
    }

    public CompoundList(List<ItemStack> list, int i) {
        this.itemList = new ItemStack[i];
        for (int o = 0; o < i; o++) itemList[o] = list.get(o);
    }

    public void put(ItemStack item) {
        for (int i = 0; i < this.itemList.length; i++) {
            if (this.itemList[i] == null) {
                this.itemList[i] = item;
                return;
            }
        }
    }

    public void set(ItemStack item, int i) {
        this.itemList[i] = item;
    }

    public ItemStack get(int i) {
        return this.itemList[i];
    }

    public void remove(int i) {
        this.itemList[i] = null;
    }

    public int firstEmptySlot() {
        for (int i = 0; i < this.size(); i ++) {
            if (this.itemList[i] == null) return i;
        }

        return -1;
    }

    public boolean getEmptySlot(int i) {
        return this.itemList[i] == null;
    }

    public boolean equalItem(CompoundList compoundList) {
        List<Item> itemList = new ArrayList<>();
        List<Item> itemList1 = new ArrayList<>();

        if (nonullSize() != compoundList.nonullSize()) return false;

        for (int i = 0; i < nonullSize(); i ++) {
            itemList.add(get(i).getItem());
            itemList1.add(compoundList.get(i).getItem());
        }

        return !itemList.retainAll(itemList1);
    }


    public boolean equal(CompoundList compoundList) {
        if (this.nonullSize() != compoundList.nonullSize()) return false;
        return !getAllItemStack().retainAll(compoundList.getAllItemStack());
    }

    public boolean excuse(CompoundList compoundList) {
        return new HashSet<>(compoundList.getAllItemStack()).containsAll(this.getAllItemStack());
    }

    public boolean excuse(List<ItemStack> itemList) {
        return new HashSet<>(itemList).containsAll(this.getAllItemStack());
    }

    public boolean excuseItem(CompoundList compoundList) {
        return new HashSet<>(compoundList.getAllItem()).containsAll(this.getAllItem());
    }

    public boolean excuseItem(List<ItemStack> itemList) {
        List<Item> list = new ArrayList<>();
        for (ItemStack item : itemList) {
            list.add(item.getItem());
        }

        if (!list.isEmpty()) return new HashSet<>(getAllItem()).containsAll(list);
        else return false;
    }

    public List<ItemStack> getAllItemStack() {
        List<ItemStack> list = new ArrayList<>();
        for (ItemStack itemStack : this.itemList) {
            if (itemStack != null) list.add(itemStack);
        }

        return list;
    }

    private List<Item> getAllItem() {
        List<Item> list = new ArrayList<>();
        for (ItemStack itemStack : this.itemList) {
            if (itemStack != null) list.add(itemStack.getItem());
        }

        return list;
    }

    public int size() {
        return this.itemList.length;
    }

    public int nonullSize() {
        int i = 0;
        for (ItemStack itemStack : this.itemList) {
            if (itemStack != null) ++i;
        }

        return i;
    }

    public void makeEmpty() {
        initList();
    }

    public Boolean isEmpty() {
        for (ItemStack itemStack : this.itemList) {
            if (itemStack != null) return false;
        }

        return true;
    }

    public void writeToNBT(NBTTagCompound nbt) {
        NBTTagList nbtList = new NBTTagList();
        nbtList.func_150303_d();
        for (ItemStack item : this.itemList) {
            if (item == null) continue;
            NBTTagCompound nbtTag = new NBTTagCompound();
            nbtList.appendTag(item.writeToNBT(nbtTag));
        }

        nbt.setTag("NonnullItemStackList", nbtList);
    }

    public void readFromNBT(NBTTagCompound nbt) {
        if (nbt.hasKey("NonnullItemStackList")) {
            NBTTagList nbtList = (NBTTagList) nbt.getTag("NonnullItemStackList");
            for (int i = 0; i < nbtList.tagCount(); i ++) {
                ItemStack item = ItemStack.loadItemStackFromNBT(nbtList.getCompoundTagAt(i));
                if (item != null)
                    put(item);
            }
        }
    }

    private void initList() {
        Arrays.fill(this.itemList, null);
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this.itemList);
    }
}
