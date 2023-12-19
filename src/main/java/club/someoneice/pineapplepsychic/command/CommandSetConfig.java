package club.someoneice.pineapplepsychic.command;

import club.someoneice.pineapplepsychic.config.ConfigUtil;
import club.someoneice.pineapplepsychic.util.Util;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class CommandSetConfig extends CommandBase {
    @Override
    public String getCommandName() {
        return "pineappleConfig";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/pineappleConfig [config name] [key] [value]";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length < 3) Util.SendMessageToPlayer.sendToPlayer(getCommandSenderAsPlayer(sender), "/pineappleConfig [config name] [key] [value]");
        CommandRunner runner = new CommandRunner(sender, args);
        runner.start();
    }

    private final class CommandRunner extends Thread {
        ICommandSender sender;
        String[] args;

        private CommandRunner(ICommandSender sender, String[] args) {
            this.sender = sender;
            this.args = args;
        }

        @Override
        public void run() {
            if (ConfigUtil.INITIALIZE.configs.containsKey(args[0])) {
                ConfigUtil.INITIALIZE.configs.get(args[0]).init();
            }
        }
    }
}
