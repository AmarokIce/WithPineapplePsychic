package club.someoneice.pineapplepsychic.util;

import club.someoneice.json.JSON;
import club.someoneice.pineapplepsychic.PineappleMain;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

public class Util {
    public static final JSON JSON_BEAN = JSON.json5;

    public static Item getItemByText(String str) {
        Item item = (Item) Item.itemRegistry.getObject(str);

        if (item == null) {
            try {
                item = Item.getItemById(Integer.parseInt(str));
            } catch (NumberFormatException e) {
                PineappleMain.LOGGER.error(e);
            }
        }

        if (item == null)
            throw new NumberInvalidException("commands.give.notFound", str);

        return item;
    }

    public static boolean itemStackEquals(ItemStack A, ItemStack B) {
        return (A == null && B == null) || (A != null && B != null && A.getItem() == B.getItem() && A.getItemDamage() == B.getItemDamage());
    }

    /**
     * Return a new message to player.
     */
    public static class SendMessageToPlayer {
        public static void sendToPlayer(EntityPlayer player, String str) {
            player.addChatMessage(new ChatComponentText(str));
        }

        public static void sendToPlayerWithTranslation(EntityPlayer player, String str) {
            player.addChatMessage(new ChatComponentTranslation(str));
        }
    }
}
