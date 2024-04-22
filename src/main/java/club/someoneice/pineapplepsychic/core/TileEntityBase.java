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

import java.lang.reflect.Field;
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

        Arrays.stream(this.getClass().getDeclaredFields())
                .filter(it -> it.isAnnotationPresent(NBTTag.class) && nbt.hasKey(it.getName()))
                .peek(it -> it.setAccessible(true))
                .forEach(it -> readField(it, nbt, this));

        this.load(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        Arrays.stream(this.getClass().getDeclaredFields())
                .filter(it -> it.isAnnotationPresent(NBTTag.class))
                .peek(it -> it.setAccessible(true))
                .forEach(it -> writeField(it, nbt, this));
        this.save(nbt);
    }

    private static void readField(Field it, NBTTagCompound nbt, TileEntityBase tile) {
        try {
            Object obj = it.get(tile);
            String typeName = obj.getClass().getSimpleName();
            if (typeName.equals(String.class.getSimpleName()))                  it.set(tile, nbt.getString(it.getName()));
            else if (typeName.equals(Integer.class.getSimpleName()))            it.set(tile, nbt.getInteger(it.getName()));
            else if (typeName.equals(Float.class.getSimpleName()))              it.set(tile, nbt.getFloat(it.getName()));
            else if (typeName.equals(Double.class.getSimpleName()))             it.set(tile, nbt.getDouble(it.getName()));
            else if (typeName.equals(Boolean.class.getSimpleName()))            it.set(tile, nbt.getBoolean(it.getName()));
            else if (typeName.equals(Long.class.getSimpleName()))               it.set(tile, nbt.getLong(it.getName()));
            else if (typeName.equals(Short.class.getSimpleName()))              it.set(tile, nbt.getShort(it.getName()));
            else if (typeName.equals(ItemStack.class.getSimpleName()))          it.set(tile, ItemStack.loadItemStackFromNBT(nbt.getCompoundTag(it.getName())));
            else if (typeName.equals(SimpleInventory.class.getSimpleName()))    it.set(tile, SimpleInventory.createAndLoadFromNBT(nbt.getCompoundTag(it.getName())));
        } catch (IllegalArgumentException | IllegalAccessException e) {
            PineappleMain.LOGGER.error(e);
        }
    }

    private static void writeField(Field it, NBTTagCompound nbt, TileEntityBase tile) {
        try {
            Object obj = it.get(tile);
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
