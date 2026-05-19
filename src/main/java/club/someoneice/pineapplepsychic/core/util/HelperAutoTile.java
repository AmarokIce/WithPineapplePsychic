package club.someoneice.pineapplepsychic.core.util;

import club.someoneice.pineapplepsychic.PineappleMain;
import club.someoneice.pineapplepsychic.lib.api.AutoRegistryTileEntity;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import cpw.mods.fml.common.ModClassLoader;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.CoreModManager;
import net.minecraft.tileentity.TileEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class HelperAutoTile {
  public static void register() {
    autoRegisterTileEntity();
  }

  private static void autoRegisterTileEntity() {
    final ModClassLoader modClassLoader = PineappleMain.modClassLoader;

    final ImmutableList<Object> knownLibraries = ImmutableList.builder().addAll(modClassLoader.getDefaultLibraries()).addAll(CoreModManager.getLoadedCoremods()).build();
    final File[] minecraftSources = modClassLoader.getParentSources();
    final HashSet<String> searchedSources = Sets.newHashSet();

    for (File minecraftSource : minecraftSources) {
      try {
        if (searchedSources.contains(minecraftSource.getAbsolutePath())) {
          continue;
        }

        searchedSources.add(minecraftSource.getAbsolutePath());
        if (!minecraftSource.isFile()) {
          readFromDirectory(minecraftSource, minecraftSource);
          return;
        }

        if (knownLibraries.contains(minecraftSource.getName())) {
          continue;
        }
        readFromZipFile(minecraftSource);
      } catch (Exception e) {
        PineappleMain.LOGGER.error("Error while reading minecraft source: {}", minecraftSource.getAbsolutePath());
        PineappleMain.LOGGER.error(e);
      }
    }
  }

  private static void readFromZipFile(File file) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
    final FileInputStream fileinputstream = new FileInputStream(file);
    final ZipInputStream zipinputstream = new ZipInputStream(fileinputstream);

    ZipEntry zipentry;
    while (Objects.nonNull(zipentry = zipinputstream.getNextEntry())) {
      String fullName = zipentry.getName().replace('\\', '/');
      int pos = fullName.lastIndexOf(47);
      String name = pos == -1 ? fullName : fullName.substring(pos + 1);

      if (zipentry.isDirectory()) {
        continue;
      }

      String className = name.replace(".class", "").replace("\\", ".").replace("/", ".");

      if (registerTileEntityByClass(className)) {
        return;
      }
    }

    fileinputstream.close();
  }

  private static void readFromDirectory(File directory, File file) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
    final  File[] files = directory.listFiles();

    if (files == null) {
      return;
    }

    for (File child : files) {
      if (child.isDirectory()) {
        readFromDirectory(child, file);
        return;
      }

      String className = ((!file.isFile() && child.getPath().startsWith(file.getPath()))
        ? child.getPath().substring(file.getPath().length() + 1)
        : ""
      ).replace(".class", "")
        .replace("\\", ".")
        .replace("/", ".");

      if (registerTileEntityByClass(className)) {
        return;
      }
    }
  }

  private static boolean registerTileEntityByClass(String className)
    throws ClassNotFoundException, InstantiationException, IllegalAccessException {
    final Class<?> clazz = Class.forName(className, true, PineappleMain.modClassLoader);
    if (!clazz.isAnnotationPresent(AutoRegistryTileEntity.class)) {
      return true;
    }

    Object tile = clazz.newInstance();
    AutoRegistryTileEntity annotation = clazz.getDeclaredAnnotation(AutoRegistryTileEntity.class);
    if (!(tile instanceof TileEntity)) {
      return true;
    }

    GameRegistry.registerTileEntity((Class<? extends TileEntity>) clazz, annotation.name().isEmpty()
      ? clazz.getName()
      : annotation.name());
    return false;
  }
}
