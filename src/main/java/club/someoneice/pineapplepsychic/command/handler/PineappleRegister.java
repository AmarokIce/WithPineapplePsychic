package club.someoneice.pineapplepsychic.command.handler;

import club.someoneice.pineapplepsychic.command.IPineappleCommand;
import com.google.common.collect.Sets;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.Set;

public class PineappleRegister {
    static final Set<IPineappleCommand> serverCommands = Sets.newHashSet();
    static final Set<IPineappleCommand> clientCommands = Sets.newHashSet();

    @SideOnly(Side.SERVER)
    public static void registryServerCommand(IPineappleCommand command) {
        serverCommands.add(command);
    }

    @SideOnly(Side.CLIENT)
    public static void registryClientCommand(IPineappleCommand command) {
        clientCommands.add(command);
    }

    public static IPineappleCommand hasCommand(String str) {
        return serverCommands.stream()
                .filter(it -> it.getCommandName().equals(str) || it.getCommandAliases().contains(str))
                .findFirst()
                .orElse(clientCommands.stream().filter(it -> it.getCommandName().equals(str) || it.getCommandAliases().contains(str))
                        .findFirst()
                        .orElse(null));
    }
}
