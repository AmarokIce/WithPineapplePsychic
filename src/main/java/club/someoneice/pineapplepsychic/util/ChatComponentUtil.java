package club.someoneice.pineapplepsychic.util;

import club.someoneice.pineapplepsychic.util.math.PosUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import java.util.List;

@SuppressWarnings({"unused", "unchecked"})
public final class ChatComponentUtil {
    private ChatComponentUtil() {
    }

    public static void sendTo(EntityPlayer player, String message) {
        player.addChatComponentMessage(new ChatComponentText(message));
    }

    public static void sendToAll(World world, String message) {
        ((List<EntityPlayer>) world.playerEntities).forEach(it -> it.addChatComponentMessage(new ChatComponentText(message)));
    }

    public static void sendTranslationTo(EntityPlayer player, String message) {
        sendTo(player, I18n.format(message));
    }

    public static void sendTranslationToAll(World world, String message) {
        sendToAll(world, I18n.format(message));
    }

    public static void sendAround(EntityPlayer player, String message, double distance) {
        List<EntityPlayer> players = (List<EntityPlayer>) player.worldObj.getEntitiesWithinAABB(EntityPlayer.class, PosUtil.asAABB(PosUtil.by(player), distance));
        players.forEach(it -> sendTranslationTo(it, message));
    }
}
