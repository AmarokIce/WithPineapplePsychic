package club.someoneice.pineapplepsychic.util;

import net.minecraft.command.NumberInvalidException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

public class Util {

    public Item getItemByText(String str) {
        Item item = (Item) Item.itemRegistry.getObject(str);

        if (item == null) {
            try {
                item = Item.getItemById(Integer.parseInt(str));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (item == null)
            throw new NumberInvalidException("commands.give.notFound", str);

        return item;
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
