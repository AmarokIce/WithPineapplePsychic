package club.someoneice.pineapplepsychic.lib.api;

import club.someoneice.pineapplepsychic.PineappleMain;
import club.someoneice.pineapplepsychic.lib.inventory.SimpleInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unchecked, unused")
public abstract class TileBase extends TileEntity {
  public TileBase() {
  }

  public TileBase(World world, int meta) {
    this.setWorldObj(world);
    this.blockMetadata = meta;
  }

  abstract public void load(NBTTagCompound nbt);

  abstract public void save(NBTTagCompound nbt);

  private static void readField(Field it, NBTTagCompound nbt, TileBase tile, NBTTag tag) {
    try {
      String name = tag.name().isEmpty() ? it.getName() : tag.name();
      Object obj = it.get(tile);
      String typeName = obj.getClass().getSimpleName();
      switch (typeName) {
        case "String": {
          it.set(tile, nbt.getString(name));
          break;
        }
        case "Integer": {
          it.set(tile, nbt.getInteger(name));
          break;
        }
        case "Boolean": {
          it.set(tile, nbt.getBoolean(name));
          break;
        }
        case "Double": {
          it.set(tile, nbt.getDouble(name));
          break;
        }
        case "Float": {
          it.set(tile, nbt.getFloat(name));
          break;
        }
        case "Long": {
          it.set(tile, nbt.getLong(name));
          break;
        }
        case "Short": {
          it.set(tile, nbt.getShort(name));
          break;
        }
        case "Byte": {
          it.set(tile, nbt.getByte(name));
          break;
        }
        case "ItemStack": {
          it.set(tile, ItemStack.loadItemStackFromNBT(nbt.getCompoundTag(name)));
          break;
        }
        case "NBTTagCompound": {
          it.set(tile, nbt.getCompoundTag(name));
          break;
        }
        case "NBTTagList": {
          it.set(tile, (NBTTagList) nbt.getTag(name));
        }
        case "SimpleInventory": {
          it.set(tile, SimpleInventory.createAndLoadFromNBT(nbt.getCompoundTag(name)));
          break;
        }
        default: {}
      }
    } catch (IllegalArgumentException | IllegalAccessException e) {
      PineappleMain.LOGGER.error(e);
    }
  }

  private static void writeField(Field it, NBTTagCompound nbt, TileBase tile, NBTTag tag) {
    try {
      String name = tag.name().isEmpty() ? it.getName() : tag.name();
      Object obj = it.get(tile);
      String typeName = obj.getClass().getSimpleName();
      switch (typeName) {
        case "String": {
          nbt.setString(name, (String) obj);
          break;
        }
        case "Integer": {
          nbt.setInteger(name, (Integer) obj);
          break;
        }
        case "Boolean": {
          nbt.setBoolean(name, (Boolean) obj);
          break;
        }
        case "Double": {
          nbt.setDouble(name, (Double) obj);
          break;
        }
        case "Float": {
          nbt.setFloat(name, (Float) obj);
          break;
        }
        case "Long": {
          nbt.setLong(name, (Long) obj);
          break;
        }
        case "Short": {
          nbt.setShort(name, (Short) obj);
          break;
        }
        case "Byte": {
          nbt.setByte(name, (Byte) obj);
          break;
        }
        case "ItemStack": {
          nbt.setTag(name, ((ItemStack) obj).writeToNBT(new NBTTagCompound()));
          break;
        }
        case "NBTTagCompound": {
          nbt.setTag(name, (NBTTagCompound) obj);
          break;
        }
        case "NBTTagList": {
          nbt.setTag(name, (NBTTagList) obj);
          break;
        }
        case "SimpleInventory": {
          nbt.setTag(name, ((SimpleInventory) obj).write());
          break;
        }
      }
    } catch (IllegalArgumentException | IllegalAccessException e) {
      PineappleMain.LOGGER.error(e);
    }
  }

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

  @Override
  public void markDirty() {
    super.markDirty();
    if (worldObj.isRemote) return;
    ((List<EntityPlayer>) this.worldObj.playerEntities).forEach(it ->
      ((EntityPlayerMP) it).playerNetServerHandler.sendPacket(this.getDescriptionPacket()));
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
