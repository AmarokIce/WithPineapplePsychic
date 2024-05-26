package club.someoneice.pineapplepsychic.common.command;

import club.someoneice.pineapplepsychic.config.ConfigData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class CommandSetConfig extends CommandBase {
    @Override
    public String getCommandName() {
        return "reload_config";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/reload_config [config name]";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length < 1) return;
        if (ConfigData.INITIALIZE.configs.containsKey(args[0]))
            ConfigData.INITIALIZE.configs.get(args[0]).reload().init();
    }
}
