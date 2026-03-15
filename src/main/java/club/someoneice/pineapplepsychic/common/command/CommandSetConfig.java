package club.someoneice.pineapplepsychic.common.command;

import club.someoneice.pineapplepsychic.config.ConfigHelper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class CommandSetConfig extends CommandBase {
  @Override
  public String getCommandName() {
    return "reloadConfig";
  }

  @Override
  public String getCommandUsage(ICommandSender sender) {
    return "/reloadConfig [config name]";
  }

  @Override
  public void processCommand(ICommandSender sender, String[] args) {
    if (args.length < 1) return;
    if (ConfigHelper.INITIALIZE.configs.containsKey(args[0]))
      ConfigHelper.INITIALIZE.configs.get(args[0]).reload().init();
  }
}
