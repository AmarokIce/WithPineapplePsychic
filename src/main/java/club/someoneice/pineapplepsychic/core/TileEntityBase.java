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
    public TileEntityBase() {
    }

    public TileEntityBase(World world, int meta) {
        this.setWorldObj(world);
        this.blockMetadata = meta;
    }

    private static void readField(Field it, NBTTagCompound nbt, TileEntityBase tile, NBTTag tag) {
        try {
            String name = tag.name().isEmpty() ? it.getName() : tag.name();
            Object obj = it.get(tile);
            String typeName = obj.getClass().getSimpleName();
            if (typeName.equals(String.class.getSimpleName())) it.set(tile, nbt.getString(name));
            else if (typeName.equals(Integer.class.getSimpleName())) it.set(tile, nbt.getInteger(name));
            else if (typeName.equals(Float.class.getSimpleName())) it.set(tile, nbt.getFloat(name));
            else if (typeName.equals(Double.class.getSimpleName())) it.set(tile, nbt.getDouble(name));
            else if (typeName.equals(Boolean.class.getSimpleName())) it.set(tile, nbt.getBoolean(name));
            else if (typeName.equals(Long.class.getSimpleName())) it.set(tile, nbt.getLong(name));
            else if (typeName.equals(Short.class.getSimpleName())) it.set(tile, nbt.getShort(name));
            else if (typeName.equals(ItemStack.class.getSimpleName()))
                it.set(tile, ItemStack.loadItemStackFromNBT(nbt.getCompoundTag(name)));
            else if (typeName.equals(SimpleInventory.class.getSimpleName()))
                it.set(tile, SimpleInventory.createAndLoadFromNBT(nbt.getCompoundTag(name)));
        } catch (IllegalArgumentException | IllegalAccessException e) {
            PineappleMain.LOGGER.error(e);
        }
    }

    private static void writeField(Field it, NBTTagCompound nbt, TileEntityBase tile, NBTTag tag) {
        try {
            String name = tag.name().isEmpty() ? it.getName() : tag.name();
            Object obj = it.get(tile);
            String typeName = obj.getClass().getSimpleName();
            if (typeName.equals(String.class.getSimpleName())) nbt.setString(name, (String) obj);
            else if (typeName.equals(Integer.class.getSimpleName())) nbt.setInteger(name, (int) obj);
            else if (typeName.equals(Float.class.getSimpleName())) nbt.setFloat(name, (float) obj);
            else if (typeName.equals(Double.class.getSimpleName())) nbt.setDouble(name, (double) obj);
            else if (typeName.equals(Boolean.class.getSimpleName())) nbt.setBoolean(name, (boolean) obj);
            else if (typeName.equals(Long.class.getSimpleName())) nbt.setLong(name, (long) obj);
            else if (typeName.equals(Short.class.getSimpleName())) nbt.setShort(name, (short) obj);
            else if (typeName.equals(ItemStack.class.getSimpleName()))
                nbt.setTag(name, ((ItemStack) obj).writeToNBT(new NBTTagCompound()));
            else if (typeName.equals(SimpleInventory.class.getSimpleName()))
                nbt.setTag(name, ((SimpleInventory) obj).write());
        } catch (IllegalArgumentException | IllegalAccessException e) {
            PineappleMain.LOGGER.error(e);
        }
    }

    abstract public void load(NBTTagCompound nbt);

    abstract public void save(NBTTagCompound nbt);

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
