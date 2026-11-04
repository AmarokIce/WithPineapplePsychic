package club.someoneice.pineapplepsychic.common.tile;

import club.someoneice.pineapplepsychic.core.PPcMain;
import club.someoneice.pineapplepsychic.api.anno.NBTTag;
import club.someoneice.pineapplepsychic.feature.TileEntityHelper;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class TileEntityBase extends TileEntity {
  public TileEntityBase() {}

  public TileEntityBase(World world, int meta) {
    this.setWorldObj(world);
    this.blockMetadata = meta;
  }

  private static void readField(Field it, NBTTagCompound nbt, TileEntityBase tile, NBTTag tag) {
    final String name = tag.value().isEmpty() ? it.getName() : tag.value();
    final boolean success = TileEntityHelper.readFromNBT(it, nbt, tile, name);
    if (!success) {
      PPcMain.LOGGER.error(
          "Can't load nbt field! value: {}, type: {}", name, it.getType().getSimpleName());
    }
  }

  private static void writeField(Field it, NBTTagCompound nbt, TileEntityBase tile, NBTTag tag) {
    final String name = tag.value().isEmpty() ? it.getName() : tag.value();
    final boolean success = TileEntityHelper.writeToNBT(it, nbt, tile, name);
    if (!success) {
      PPcMain.LOGGER.error(
          "Can't save nbt field! value: {}, type: {}", name, it.getType().getSimpleName());
    }
  }

  public void load(NBTTagCompound nbt) {}
  public void save(NBTTagCompound nbt) {}

  @Override
  public void readFromNBT(NBTTagCompound nbt) {
    super.readFromNBT(nbt);
    Arrays.stream(this.getClass().getDeclaredFields())
        .filter(it -> it.isAnnotationPresent(NBTTag.class) && nbt.hasKey(it.getName()))
        .peek(it -> it.setAccessible(true))
        .forEach(it -> readField(it, nbt, this, it.getDeclaredAnnotation(NBTTag.class)));
    this.load(nbt);
  }

  @Override
  public void writeToNBT(NBTTagCompound nbt) {
    super.writeToNBT(nbt);
    Arrays.stream(this.getClass().getDeclaredFields())
        .filter(it -> it.isAnnotationPresent(NBTTag.class))
        .peek(it -> it.setAccessible(true))
        .forEach(it -> writeField(it, nbt, this, it.getDeclaredAnnotation(NBTTag.class)));
    this.save(nbt);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void markDirty() {
    super.markDirty();
    if (worldObj.isRemote) {
      return;
    }
    
    final List<EntityPlayerMP> playerList = (List<EntityPlayerMP>) this.worldObj.playerEntities;
    playerList.forEach(it -> it.playerNetServerHandler.sendPacket(this.getDescriptionPacket()));
  }

  @Override
  public Packet getDescriptionPacket() {
    if (!this.canUpdate()) {
      return null;
    }

    NBTTagCompound nbttagcompound = new NBTTagCompound();
    this.writeToNBT(nbttagcompound);
    return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 5, nbttagcompound);
  }

  @Override
  public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
    super.onDataPacket(net, pkt);
    this.readFromNBT(pkt.func_148857_g());
  }

  @Override
  public boolean canUpdate() {
    return false;
  }
}
