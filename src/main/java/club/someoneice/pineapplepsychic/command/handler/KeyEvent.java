package club.someoneice.pineapplepsychic.command.handler;

import club.someoneice.pineapplepsychic.command.IPineappleCommand;
import club.someoneice.pineapplepsychic.util.ReflectUtil;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.ServerChatEvent;
import org.lwjgl.input.Keyboard;


public class KeyEvent {
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onPlayerTabKeyOn(InputEvent.KeyInputEvent event) {
        // this.playerEntity.playerNetServerHandler.sendPacket(new S3APacketTabComplete((String[])arraylist.toArray(new String[arraylist.size()])));

        if (!Keyboard.isKeyDown(Keyboard.KEY_TAB)) return;
        Minecraft mc = FMLClientHandler.instance().getClient();
        if (!(mc.currentScreen instanceof GuiChat)) return;
        GuiChat gui = (GuiChat) mc.currentScreen;
        GuiTextField text;
        try {
            text = ReflectUtil.reflectField(gui.getClass(), "inputField");
        } catch (Exception ignored) {
            return;
        }
        String chatMessage = text.getText().substring(0, text.getCursorPosition());
        if (!chatMessage.startsWith(">")) return;
        String[] tree = chatMessage.substring(1).split(" ");
        IPineappleCommand command;
        if ((command = PineappleRegister.hasCommand(tree[0])) != null) {
            // command.processCommand(mc.thePlayer, tree);
            // TODO GUI
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onChatClient(ClientChatReceivedEvent event) {
        String text = event.message.getUnformattedText();
        if (!text.startsWith(">")) {
        }

    }

    @SideOnly(Side.SERVER)
    @SubscribeEvent
    public void onChatServer(ServerChatEvent event) {
        String text = event.message;
        if (!text.startsWith(">")) {
        }
    }
}
