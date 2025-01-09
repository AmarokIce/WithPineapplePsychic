package club.someoneice.pineapplepsychic.common.command;

import club.someoneice.pineapplepsychic.util.NBTCore;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.Vec3;

import java.io.IOException;


public class CommandSetNBT extends CommandBase {
    @Override
    public String getCommandName() {
        return "pineappleNBT";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/pineappleNBT";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        try {
            String input = args[0];
            switch (input) {
                case "set":
                    NBTCore.cmdWriteToNBT(getPlayer(sender, args[1]));
                    break;
                case "read":
                    NBTCore.cmdReadFromNBT(getPlayer(sender, args[1]), new NBTCore.PlayerVec(Vec3.createVectorHelper(sender.getPlayerCoordinates().posX, sender.getPlayerCoordinates().posY, sender.getPlayerCoordinates().posZ), sender.getEntityWorld().provider.dimensionId));
                    break;
                case "setself":
                    NBTCore.cmdWriteToNBT(getCommandSenderAsPlayer(sender));
                    break;
                case "readself":
                    NBTCore.cmdReadFromNBT(getCommandSenderAsPlayer(sender), new NBTCore.PlayerVec(Vec3.createVectorHelper(sender.getPlayerCoordinates().posX, sender.getPlayerCoordinates().posY, sender.getPlayerCoordinates().posZ), sender.getEntityWorld().provider.dimensionId));
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException("无法找到目标或目标的NBT文件. ");
        }
    }
}
