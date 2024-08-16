package club.someoneice.pineapplepsychic.util;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public final class MultiBlock {
    public static final MultiBlock INITIALIZE = new MultiBlock();

    private MultiBlock() {
    }

    public boolean checkMultiBlock(Map<Integer, List<Block>> maps, EntityPlayer player, World world, ChunkCoordinates basePos, int ox, int oz, ForgeDirection face, Block target, int maxY) {
        if (maps.size() < maxY) return false;
        Processor processor = new Processor(maps);

        if (face == ForgeDirection.EAST) {
            ChunkCoordinates originPos = new ChunkCoordinates(basePos.posX + ox, basePos.posY, basePos.posZ - oz);
            for (int y = 0; y < maxY; y++) {
                int i = 0;
                for (int x = 0; x > -5; x--)
                    for (int z = 0; z < 5; z++) {
                        if (!processor.checkBlockInMap(world, new ChunkCoordinates(originPos.posX + x, originPos.posY + y, originPos.posZ + z), y, i))
                            return false;
                        i++;
                    }
            }

            for (int y = 0; y < maxY; y++)
                for (int x = 0; x > -5; x--)
                    for (int z = 0; z < 5; z++) {
                        world.setBlock(originPos.posX + x, originPos.posY + y, originPos.posZ + z, Blocks.air);
                    }
        } else if (face == ForgeDirection.SOUTH) {
            ChunkCoordinates originPos = new ChunkCoordinates(basePos.posX + oz, basePos.posY, basePos.posZ + ox);
            for (int y = 0; y < maxY; y++) {
                int i = 0;
                for (int z = 0; z > -5; z--)
                    for (int x = 0; x > -5; x--) {
                        if (!processor.checkBlockInMap(world, new ChunkCoordinates(originPos.posX + x, originPos.posY + y, originPos.posZ + z), y, i))
                            return false;
                        i++;
                    }
            }

            for (int y = 0; y < maxY; y++)
                for (int z = 0; z > -5; z--)
                    for (int x = 0; x > -5; x--) {
                        world.setBlock(originPos.posX + x, originPos.posY + y, originPos.posZ + z, Blocks.air);
                    }
        } else if (face == ForgeDirection.WEST) {
            ChunkCoordinates originPos = new ChunkCoordinates(basePos.posX - ox, basePos.posY, basePos.posZ + oz);
            for (int y = 0; y < maxY; y++) {
                int i = 0;
                for (int x = 0; x < 5; x++)
                    for (int z = 0; z > -5; z--) {
                        if (!processor.checkBlockInMap(world, new ChunkCoordinates(originPos.posX + x, originPos.posY + y, originPos.posZ + z), y, i))
                            return false;
                        i++;
                    }
            }

            for (int y = 0; y < maxY; y++)
                for (int x = 0; x < 5; x++)
                    for (int z = 0; z > -5; z--) {
                        world.setBlock(originPos.posX + x, originPos.posY + y, originPos.posZ + z, Blocks.air);
                    }
        } else if (face == ForgeDirection.NORTH) {
            ChunkCoordinates originPos = new ChunkCoordinates(basePos.posX - oz, basePos.posY, basePos.posZ - ox);
            for (int y = 0; y < maxY; y++) {
                int i = 0;
                for (int z = 0; z < 5; z++)
                    for (int x = 0; x < 5; x++) {
                        if (!processor.checkBlockInMap(world, new ChunkCoordinates(originPos.posX + x, originPos.posY + y, originPos.posZ + z), y, i))
                            return false;
                        i++;
                    }
            }

            for (int y = 0; y < maxY; y++)
                for (int z = 0; z < 5; z++)
                    for (int x = 0; x < 5; x++) {
                        world.setBlock(originPos.posX + x, originPos.posY + y, originPos.posZ + z, Blocks.air);
                    }
        }

        if (target != null && target != Blocks.air) {
            world.setBlock(basePos.posX, basePos.posY, basePos.posZ, target);
        }

        return true;
    }

    private static final class Processor {
        Map<Integer, List<Block>> maps;

        public Processor(Map<Integer, List<Block>> maps) {
            this.maps = maps;
        }

        public boolean checkBlockInMap(World world, ChunkCoordinates pos, int y, int size) {
            Block block = maps.get(y).get(size);
            if (block == null || block == Blocks.air) return true;
            else return world.getBlock(pos.posX, pos.posY, pos.posZ).isAssociatedBlock(block);
        }
    }
}

