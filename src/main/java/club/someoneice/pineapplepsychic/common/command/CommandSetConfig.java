package club.someoneice.pineapplepsychic.common.command;

import club.someoneice.pineapplepsychic.config.ConfigUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class CommandSetConfig extends CommandBase {
    @Override
    public String getCommandName() {
        return "pineappleConfig";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/pineappleConfig [config name]";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length < 1) return;
        try {
            if (ConfigUtil.INITIALIZE.configs.containsKey(args[0])) {
                ConfigUtil.INITIALIZE.configs.get(args[0]).reload().init();
            }
        } catch (Exception ignored) {}
    }
}
