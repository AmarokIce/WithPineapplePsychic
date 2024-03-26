package club.someoneice.pineapplepsychic.core;

import club.someoneice.pineapplepsychic.PineappleMain;
import club.someoneice.pineapplepsychic.api.NBTTag;
import club.someoneice.pineapplepsychic.inventory.SimpleInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unchecked, unused")
public abstract class TileEntityBase extends TileEntity {
    public TileEntityBase() {}

    public TileEntityBase(World world, int meta) {
        this.setWorldObj(world);
        this.blockMetadata = meta;
    }

    abstract public void load(NBTTagCompound nbt);
    abstract public void save(NBTTagCompound nbt);

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        Arrays.stream(this.getClass().getDeclaredFields()).filter(it -> it.isAnnotationPresent(NBTTag.class)).forEach(it -> {
            try {
                if (!nbt.hasKey(it.getName())) return;

                it.setAccessible(true);
                Object obj = it.get(this);
                String typeName = obj.getClass().getSimpleName();
                if (typeName.equals(String.class.getSimpleName()))                  it.set(this, nbt.getString(it.getName()));
                else if (typeName.equals(Integer.class.getSimpleName()))            it.set(this, nbt.getInteger(it.getName()));
                else if (typeName.equals(Float.class.getSimpleName()))              it.set(this, nbt.getFloat(it.getName()));
                else if (typeName.equals(Double.class.getSimpleName()))             it.set(this, nbt.getDouble(it.getName()));
                else if (typeName.equals(Boolean.class.getSimpleName()))            it.set(this, nbt.getBoolean(it.getName()));
                else if (typeName.equals(Long.class.getSimpleName()))               it.set(this, nbt.getLong(it.getName()));
                else if (typeName.equals(Short.class.getSimpleName()))              it.set(this, nbt.getShort(it.getName()));
                else if (typeName.equals(ItemStack.class.getSimpleName()))          it.set(this, ItemStack.loadItemStackFromNBT(nbt.getCompoundTag(it.getName())));
                else if (typeName.equals(SimpleInventory.class.getSimpleName()))    it.set(this, SimpleInventory.createAndLoadFromNBT(nbt.getCompoundTag(it.getName())));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                PineappleMain.LOGGER.error(e);
            }
        });

        this.load(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        Arrays.stream(this.getClass().getDeclaredFields()).filter(it -> it.isAnnotationPresent(NBTTag.class)).forEach(it -> {
            try {
                Object obj = it.get(this);
                String typeName = obj.getClass().getSimpleName();
                if (typeName.equals(String.class.getSimpleName()))                  nbt.setString(it.getName(),     (String) obj);
                else if (typeName.equals(Integer.class.getSimpleName()))            nbt.setInteger(it.getName(),    (int) obj);
                else if (typeName.equals(Float.class.getSimpleName()))              nbt.setFloat(it.getName(),      (float) obj);
                else if (typeName.equals(Double.class.getSimpleName()))             nbt.setDouble(it.getName(),     (double) obj);
                else if (typeName.equals(Boolean.class.getSimpleName()))            nbt.setBoolean(it.getName(),    (boolean) obj);
                else if (typeName.equals(Long.class.getSimpleName()))               nbt.setLong(it.getName(),       (long) obj);
                else if (typeName.equals(Short.class.getSimpleName()))              nbt.setShort(it.getName(),      (short) obj);
                else if (typeName.equals(ItemStack.class.getSimpleName()))          nbt.setTag(it.getName(),        ((ItemStack) obj).writeToNBT(new NBTTagCompound()));
                else if (typeName.equals(SimpleInventory.class.getSimpleName()))    nbt.setTag(it.getName(),        ((SimpleInventory) obj).write());
            } catch (IllegalArgumentException | IllegalAccessException e) {
                PineappleMain.LOGGER.error(e);
            }
        });

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
