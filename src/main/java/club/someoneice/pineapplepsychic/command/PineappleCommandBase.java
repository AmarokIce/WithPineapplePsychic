package club.someoneice.pineapplepsychic.command;

import com.google.common.collect.Lists;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.UserListOpsEntry;

import java.util.List;

public abstract class PineappleCommandBase implements IPineappleCommand {
    @Override
    public List<String> getCommandAliases() {
        return Lists.newArrayList();
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        if (!sender.getEntityWorld().isRemote) {
            EntityPlayerMP player = (EntityPlayerMP) sender;
            UserListOpsEntry userlistopsentry = (UserListOpsEntry) player.mcServer.getConfigurationManager().func_152603_m().func_152683_b(player.getGameProfile());
            return userlistopsentry != null ? userlistopsentry.func_152644_a() >= this.getRequiredPermissionLevel() : player.mcServer.getOpPermissionLevel() >= this.getRequiredPermissionLevel();
        } else return true;
    }

    @Override
    public List<String> addTabCompletionOptions(EntityPlayerMP sender, String[] parameterTree) {
        return Lists.newArrayList();
    }

    @Override
    public int compareTo(IPineappleCommand o) {
        return this.getCommandName().compareTo(o.getCommandName());
    }
}
