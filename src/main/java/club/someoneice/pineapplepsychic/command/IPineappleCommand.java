package club.someoneice.pineapplepsychic.command;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.List;

public interface IPineappleCommand extends Comparable<IPineappleCommand> {
    String getCommandName();

    String getCommandUsingHelper();

    List<String> getCommandAliases();

    void processCommand(EntityPlayer sender, String[] parameterTree);

    int getRequiredPermissionLevel();

    boolean canCommandSenderUseCommand(ICommandSender sender);

    List<String> addTabCompletionOptions(EntityPlayerMP sender, String[] parameterTree);
}
