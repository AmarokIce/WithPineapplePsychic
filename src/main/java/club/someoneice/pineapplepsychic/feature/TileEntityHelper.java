package club.someoneice.pineapplepsychic.feature;

import club.someoneice.pineapplepsychic.core.PPcMain;
import club.someoneice.pineapplepsychic.api.anno.NBTTag;
import club.someoneice.pineapplepsychic.api.anno.register.AutoRegisterTileEntity;
import club.someoneice.pineapplepsychic.common.tile.TileEntityBase;
import club.someoneice.pineapplepsychic.feature.function.FieldHandler;
import com.google.common.collect.Maps;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * @see AutoRegisterTileEntity AutoTileEntity
 * @see TileEntityBase TileBase
 */
public class TileEntityHelper {
  public static Map<String, FieldCodec<TileEntity, NBTTagCompound>> FIELD_MAPPING =
      Maps.newHashMap();

  public static void init() {
    // TODO
  }

  /**
   * Load and format the data in field.
   *
   * @param field The field in class instances TileEntity.
   * @param nbt From {@link TileEntity#readFromNBT}'s NBTTagComponent.
   * @param tile The tile in load.
   * @param name The sign value, Usually take from {@link
   *     NBTTag NBTTag}
   * @see NBTTag NBTTag
   * @see AutoRegisterTileEntity AutoRegisterTileEntity
   */
  public static boolean readFromNBT(
      @Nonnull final Field field,
      @Nonnull final NBTTagCompound nbt,
      @Nonnull final TileEntity tile,
      @Nonnull final String name) {
    try {
      final String signName = name.isEmpty() ? field.getName() : name;
      final Object obj = field.get(tile);
      final String typeName = obj.getClass().getSimpleName();

      @CheckForNull
      final FieldHandler<TileEntity, NBTTagCompound> handler =
          ObjectLambda.objectLet(FIELD_MAPPING.get(typeName), FieldCodec::getDecoder);

      if (Objects.isNull(handler)) {
        PPcMain.LOGGER.error(
            "Can't load nbt data from tile entity! value: {}, type: {}, tile: {}",
            signName,
            typeName,
            tile);
        return false;
      }

      return handler.handle(field, tile, nbt, signName);
    } catch (final IllegalAccessException e) {
      PPcMain.LOGGER.error("Can't load nbt data from tile entity!", e);
      return false;
    }
  }

  /**
   * Write and format the data from field.
   *
   * @param field The field in class instances TileEntity.
   * @param nbt From {@link TileEntity#readFromNBT}'s NBTTagComponent.
   * @param tile The tile in load.
   * @param name The sign value, Usually take from {@link
   *     NBTTag NBTTag}
   * @see NBTTag NBTTag
   * @see AutoRegisterTileEntity AutoRegisterTileEntity
   */
  public static boolean writeToNBT(
      @Nonnull final Field field,
      @Nonnull final NBTTagCompound nbt,
      @Nonnull final TileEntity tile,
      @Nonnull final String name) {
    try {
      final String signName = name.isEmpty() ? field.getName() : name;
      final Object obj = field.get(tile);
      final String typeName = obj.getClass().getSimpleName();

      @CheckForNull
      final FieldHandler<TileEntity, NBTTagCompound> handler =
          ObjectLambda.objectLet(FIELD_MAPPING.get(typeName), FieldCodec::getEncoder);
      if (Objects.isNull(handler)) {
        PPcMain.LOGGER.error(
            "Can't write nbt data from tile entity! value: {}, type: {}, tile: {}",
            signName,
            typeName,
            tile);
        return false;
      }

      return handler.handle(field, tile, nbt, signName);
    } catch (final IllegalAccessException e) {
      PPcMain.LOGGER.error("Can't write nbt data from tile entity!", e);
      return false;
    }
  }
}
