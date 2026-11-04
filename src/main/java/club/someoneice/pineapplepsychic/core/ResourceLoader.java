package club.someoneice.pineapplepsychic.core;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.ReflectionHelper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javax.annotation.CheckForNull;

public class ResourceLoader {
  public static final ImmutableSet<String> RESOURCES;

  static {
    final ImmutableSet.Builder<String> builder = new ImmutableSet.Builder<>();
    final File modDir =
        ReflectionHelper.getPrivateValue(Loader.class, Loader.instance(), "canonicalModsDir");
    final File[] files = modDir.listFiles();
    assert Objects.nonNull(files);

    final List<JarFile> jars = Lists.newArrayList();
    Arrays.stream(files)
        .filter(File::isFile)
        .filter(it -> it.getName().endsWith(".jar"))
        .map(ResourceLoader::createJarFile)
        .filter(Objects::nonNull)
        .peek(jars::add)
        .flatMap(JarFile::stream)
        .filter(ResourceLoader::isFileAndNotClass)
        .map(JarEntry::getName)
        .forEach(builder::add);
    jars.forEach(ResourceLoader::closeJarFile);

    RESOURCES = builder.build();
  }

  @CheckForNull
  public static InputStream getInputStreamFromJar(final String path) {
    return Loader.instance().getClass().getResourceAsStream("/" + path);
  }
  
  @CheckForNull
  private static JarFile createJarFile(final File file) {
    try {
      return new JarFile(file);
    } catch (final IOException e) {
      PPcMain.LOGGER.error(e);
      return null;
    }
  }

  private static void closeJarFile(final JarFile file) {
    try {
      file.close();
    } catch (final IOException e) {
      PPcMain.LOGGER.error(e);
    }
  }

  private static boolean isFileAndNotClass(final JarEntry entry) {
    return !entry.isDirectory() && !entry.getName().endsWith(".class");
  }
}
