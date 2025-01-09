package club.someoneice.pineapplepsychic.util.math;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;

public final class PosUtil {
    private PosUtil() {
    }

    public static ChunkPosition of(int x, int y, int z) {
        return new ChunkPosition(x, y, z);
    }

    public static ChunkPosition of(double x, double y, double z) {
        return new ChunkPosition(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z));
    }

    public static ChunkPosition by(EntityPlayer player) {
        return of(player.posX, player.posY, player.posZ);
    }

    public static AxisAlignedBB asAABB(ChunkPosition pos, double distance) {
        return AxisAlignedBB.getBoundingBox(pos.chunkPosX - distance, pos.chunkPosY - distance, pos.chunkPosZ - distance, pos.chunkPosX + distance, pos.chunkPosY + distance, pos.chunkPosZ + distance);
    }
}
