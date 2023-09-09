package club.someoneice.pineapplepsychic.command;

import club.someoneice.pineapplepsychic.config.ConfigBean;
import club.someoneice.pineapplepsychic.util.Util;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

import java.io.File;
import java.util.HashMap;

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
            IPineappleConfig config;
            HashMap<String, Object> map;
            for (String str : ConfigBean.configMap.keySet()) {
                if (str.equals(args[0])) {
                    String path = System.getProperty("user.dir") + File.separator + "config" + File.separator + str + ".json";
                    ConfigBean.JsonHelper helper = new ConfigBean.JsonHelper(path);
                    map = (HashMap<String, Object>) helper.readFronJson();
                    for (String key : map.keySet()) {
                        if (args[1].equals(key)) {
                            try {
                                if (map.get(key) instanceof String) {
                                    map.put(key, args[2]);
                                } else if (map.get(key) instanceof Integer) {
                                    map.put(key, Integer.valueOf(args[2]));
                                } else if (map.get(key) instanceof Float) {
                                    map.put(key, Float.valueOf(args[2]));
                                } else if (map.get(key) instanceof Double) {
                                    map.put(key, Double.valueOf(args[2]));
                                } else if (map.get(key) instanceof Boolean) {
                                    map.put(key, Boolean.valueOf(args[2]));
                                }

                                helper.writeToJson(map);
                                break;
                            } catch (NumberFormatException e) {
                                Util.SendMessageToPlayer.sendToPlayer(getCommandSenderAsPlayer(sender), "An error in value:" + args[2]);
                            }
                        }
                    }

                    ConfigBean.configMap.get(str).init();
                    break;
                }
            }
        }
    }
}
