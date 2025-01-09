package club.someoneice.pineapplepsychic.util;

import club.someoneice.cookie.util.ObjectUtil;
import club.someoneice.json.JSON;
import club.someoneice.pineapplepsychic.PineappleMain;
import club.someoneice.pineapplepsychic.inventory.SimpleInventory;
import com.google.common.collect.Lists;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public final class Util {
    public static final JSON JSON_BEAN = JSON.json5;

    private Util() {
    }

    public static Item getItemByText(String str) {
        Item item = (Item) Item.itemRegistry.getObject(str);

        if (item == null) {
            try {
                item = Item.getItemById(Integer.parseInt(str));
            } catch (NumberFormatException e) {
                PineappleMain.LOGGER.error(e);
            }
        }

        if (item == null) {
            throw new NumberInvalidException("commands.give.notFound", str);
        }

        return item;
    }

    public static boolean itemStackEquals(ItemStack A, ItemStack B) {
        if (Objects.isNull(A) && Objects.isNull(B)) {
            return true;
        }

        if (Objects.isNull(A) || Objects.isNull(B)) {
            return false;
        }

        boolean anyMetaFlag = A.getItemDamage() == Short.MAX_VALUE || B.getItemDamage() == Short.MAX_VALUE;
        return A.getItem() == B.getItem() && (anyMetaFlag || A.getItemDamage() == B.getItemDamage());
    }

    public static boolean isFakeItemStack(ItemStack item) {
        return Objects.isNull(item) || Objects.isNull(item.getItem());
    }

    public static void giveOrThrowOut(EntityPlayer player, ItemStack item) {
        if (player.inventory.addItemStackToInventory(item)) return;
        if (player.worldObj.isRemote) return;
        player.worldObj.spawnEntityInWorld(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, item));
    }

    public static void itemThrowOut(World world, ChunkPosition pos, ItemStack... item) {
        itemThrowOut(world, pos, ObjectUtil.objectLet(Lists.newArrayList(), it -> {
            it.addAll(Arrays.asList(item));
        }));
    }

    public static void itemThrowOut(World world, ChunkPosition pos, List<ItemStack> item) {
        if (world.isRemote) return;
        item.removeIf(Objects::isNull);
        item.removeIf(Util::isFakeItemStack);
        item.forEach(it -> world.spawnEntityInWorld(new EntityItem(world, pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ, it)));
    }

    @SuppressWarnings("all")
    public static SimpleInventory getInvFromItemStack(ItemStack item, int size) {
        SimpleInventory inventory = new SimpleInventory(size);
        if (item == null || item.getTagCompound() == null) return inventory;
        if (item.getTagCompound().hasKey("inv_data"))
            inventory.load(item.getTagCompound().getCompoundTag("inv_data"));
        return inventory;
    }

    @SuppressWarnings("all")
    public static void setInvFromItemStack(ItemStack item, SimpleInventory inventory) {
        if (item == null) return;
        if (item.getTagCompound() == null) item.stackTagCompound = new NBTTagCompound();
        item.getTagCompound().setTag("inv_data", inventory.write());
    }

    public static void sendToPlayer(EntityPlayer player, String str) {
        player.addChatMessage(new ChatComponentText(str));
    }

    public static void sendToPlayerWithTranslation(EntityPlayer player, String str) {
        player.addChatMessage(new ChatComponentTranslation(str));
    }
}
