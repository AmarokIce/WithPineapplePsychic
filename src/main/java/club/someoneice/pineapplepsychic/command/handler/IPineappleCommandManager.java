package club.someoneice.pineapplepsychic.command.handler;

import club.someoneice.pineapplepsychic.command.IPineappleCommand;
import net.minecraft.command.ICommandSender;

public abstract class IPineappleCommandManager {

    abstract public void executeCommand(IPineappleCommand command, ICommandSender p_71556_1_, String p_71556_2_);

    abstract public void canPlayerUseCommand(IPineappleCommand command, ICommandSender sender);
}
