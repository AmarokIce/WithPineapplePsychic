package club.someoneice.pineapplepsychic.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.*;
import net.minecraft.util.Vec3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class NBTCore {
    private static final String NBTPath = System.getProperty("user.dir") + File.separator +"NBTFile";

    public static void cmdWriteToNBT(EntityPlayer player) {
        new NBTHelper(player).run();

    }

    public static void cmdReadFromNBT(EntityPlayer player, PlayerVec pos) throws IOException {
        File input = new File(NBTPath, player.getDisplayName() + ".gzip");
        if (!input.exists() || !input.isFile()) return;
        NBTTagCompound tag = CompressedStreamTools.readCompressed(Files.newInputStream(input.toPath()));
        tag.setInteger("Dimension", pos.DIMId);
        tag.setTag("Pos", newDoubleNBTList(pos.playerPos.xCoord, pos.playerPos.yCoord, pos.playerPos.zCoord));
        tag.setTag("Rotation", newFloatNBTList(player.rotationYaw, player.rotationPitch));

        player.readFromNBT(tag);
    }

    private static NBTTagList newDoubleNBTList(double ... args) {
        NBTTagList nbttaglist = new NBTTagList();
        for (double d1 : args) nbttaglist.appendTag(new NBTTagDouble(d1));

        return nbttaglist;
    }

    private static NBTTagList newFloatNBTList(float ... args) {
        NBTTagList nbttaglist = new NBTTagList();
        for (float f1 : args) nbttaglist.appendTag(new NBTTagFloat(f1));

        return nbttaglist;
    }

    public static final class PlayerVec {
        public Vec3 playerPos;
        public int DIMId;

        public PlayerVec(Vec3 vec3, int DIMId) {
            this.playerPos = vec3;
            this.DIMId = DIMId;
        }
    }

    static class NBTHelper extends Thread {
        private EntityPlayer player;

        NBTHelper(EntityPlayer player) {
            this.player = player;
        }

        @Override
        public void run() {
            try {
                NBTTagCompound tag = new NBTTagCompound();
                player.writeToNBT(tag);
                File output = new File(NBTPath, player.getDisplayName() + ".gzip");

                if (!output.exists() && !output.getParentFile().isDirectory()) output.getParentFile().mkdirs();
                if (!output.isFile()) output.createNewFile();

                CompressedStreamTools.writeCompressed(tag, Files.newOutputStream(output.toPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}