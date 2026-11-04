package club.someoneice.pineapplepsychic.api.anno;

import club.someoneice.pineapplepsychic.common.tile.TileEntityBase;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * NBTTag is used to mark any fields that need to be automatically saved to an {@link
 * net.minecraft.nbt.NBTTagCompound NBTTagCompound}. Its parser is managed by {@link
 * club.someoneice.pineapplepsychic.feature.TileEntityHelper#FIELD_MAPPING
 * TileEntityHelper#FIELD_MAPPING}. <br>
 * When a field is marked with NBTTag and the current {@link net.minecraft.tileentity.TileEntity
 * TileEntity} class has already implemented {@link TileEntityBase}, it should handle them
 * automatically.
 *
 * @see TileEntityBase
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NBTTag {
  String value() default "";
}
