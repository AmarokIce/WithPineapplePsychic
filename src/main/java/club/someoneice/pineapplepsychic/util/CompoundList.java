package club.someoneice.pineapplepsychic.util;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

public class CompoundList {
    private final List<ItemStack> baseList;

    public CompoundList(ItemStack ... item) {
        this.baseList = Lists.newCopyOnWriteArrayList(Arrays.asList(item));
    }

    public ItemStack get(int slot) {
        return this.baseList.get(slot);
    }

    public ItemStack getCopy(int slot) {
        return this.baseList.get(slot).copy();
    }

    public boolean contains(ItemStack item) {
        return this.baseList.contains(item);
    }

    public boolean contains(Item item) {
        return this.asItemList().contains(item);
    }

    public boolean containsAll(CompoundList list) {
        return new HashSet<>(this.baseList).containsAll(list.baseList);
    }

    public boolean containsAll(Collection<ItemStack> ItemList) {
        ArrayList<Item> itm = this.asItemList();
        for (ItemStack it : ItemList) {
            if (!itm.contains(it.getItem())) return false;
        }

        return true;
    }

    public boolean containsAllWithMeta(Collection<ItemStack> ItemList) {
        for (ItemStack it1 : ItemList) {
            for (ItemStack it2 : this.baseList) {
                if (it1.getItem() == it2.getItem()
                        || it1.stackSize == it2.stackSize
                        || it1.getItemDamage() == it2.getItemDamage()
                ) break;
                return false;
            }
        }

        return true;
    }

    public void add(ItemStack item) {
        this.baseList.add(item);
    }

    public void add(int i, ItemStack item) {
        this.baseList.add(i, item);
    }

    public void add(Item item) {
        this.baseList.add(new ItemStack(item));
    }

    public int size() {
        return this.baseList.size();
    }

    public ArrayList<ItemStack> getListCopy() {
        return Lists.newArrayList(this.baseList);
    }

    public void addAll(Collection<? extends ItemStack> ItemList) {
        checkNotNull(ItemList);
        this.baseList.addAll(ItemList);
    }

    public void remove(int i) {
        this.baseList.remove(i);
    }

    public void remove(ItemStack item) {
        this.baseList.remove(item);
    }

    public void remove(Item item) {
        for (ItemStack it : this.baseList) {
            if (it.getItem().equals(item)) {
                this.baseList.remove(it);
                return;
            }
        }
    }

    public CompoundList copy() {
        CompoundList list = new CompoundList();
        list.addAll(this.getListCopy());
        return list;
    }

    public ArrayList<Item> asItemList() {
        ArrayList<Item> list = Lists.newArrayList();
        this.baseList.forEach(it -> list.add(it.getItem()));
        return list;
    }

    public NBTTagCompound writeToNbt(NBTTagCompound nbt) {
        NBTTagList list = new NBTTagList();
        for (ItemStack item : this.baseList)
            list.appendTag(item.writeToNBT(new NBTTagCompound()));

        nbt.setTag("CompoundList", list);
        return nbt;
    }

    public NBTTagCompound writeToNbt() {
        NBTTagCompound nbt = new NBTTagCompound();
        NBTTagList list = new NBTTagList();
        this.baseList.forEach(it -> list.appendTag(it.copy().writeToNBT(new NBTTagCompound())));

        nbt.setTag("CompoundList", list);
        return nbt;
    }


    public void readFromNbt(NBTTagCompound nbt) {
        if (nbt.hasKey("CompoundList")) {
            NBTTagList list = (NBTTagList) nbt.getTag("CompoundList");
            for (int i = 0; i < list.tagCount(); i ++) {
                this.baseList.add(ItemStack.loadItemStackFromNBT(list.getCompoundTagAt(i)));
            }
        }
    }

    @Override
    public String toString() {
        List<String> list = Lists.newArrayList();
        this.baseList.forEach(it -> list.add(it.getItem().getUnlocalizedName()));
        return new Gson().toJson(list);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        else if (obj instanceof CompoundList) return this.containsAll((CompoundList) obj);
        else if (obj instanceof List) return this.containsAll((List) obj);
        else return false;
    }
}
