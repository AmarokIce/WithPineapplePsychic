package club.someoneice.pineapplepsychic;

import club.someoneice.pineapplepsychic.api.AutoRegistryTileEntity;
import club.someoneice.pineapplepsychic.api.IPineappleConfig;
import club.someoneice.pineapplepsychic.config.ConfigBeanV2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModClassLoader;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.CoreModManager;
import net.minecraft.tileentity.TileEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@SuppressWarnings("unchecked")
@Mod(modid = PineappleMain.MODID, name = PineappleMain.NAME, version = PineappleMain.VERSION, useMetadata = true)
public class PineappleMain {
    public static final String MODID = "pineapple_psychic";
    public static final String NAME = "Pineapple Psychic";
    public static final String VERSION = "@VERSION@";
    public static final Logger LOGGER = LogManager.getLogger("[Pineapple Psychic]");

    @Mod.EventHandler
    public static void onPreInitialization(FMLPreInitializationEvent event) {
        Lists.newArrayList("",
                "====================================================================================================",
                "|| .---.  _                                .-.             .---.                  .-.    _        ||",
                "|| : .; ::_;                               : :             : .; :                 : :   :_;       ||",
                "|| :  _.'.-.,-.,-. .--.  .--.  .---. .---. : :   .--.      :  _.'.--. .-..-. .--. : `-. .-. .--.  ||",
                "|| : :   : :: ,. :' '_.'' .; ; : .; `: .; `: :_ ' '_.'     : :  `._-.': :; :'  ..': .. :: :'  ..' ||",
                "|| :_;   :_;:_;:_;`.__.'`.__,_;: ._.': ._.'`.__;`.__.'     :_;  `.__.'`._. ;`.__.':_;:_;:_;`.__.' ||",
                "||                             : :   : :                               .-. :                      ||",
                "||                             :_;   :_;                               `._.'                      ||",
                "====================================================================================================",
                "",
                "Pineapple Psychic is loading now."
        ).forEach(LOGGER::info);

        new PineappleConfig();
        try {
            autoRegisterTileEntity();
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    @Mod.EventHandler
    public static void onInitialization(FMLPreInitializationEvent event) {
    }

    @Mod.EventHandler
    public static void onServerInitialization(FMLServerStartingEvent event) {
        // event.registerServerCommand(new CommandSetConfig());
    }

    public static class PineappleConfig extends ConfigBeanV2 implements IPineappleConfig {
        public static boolean testBoolean = false;

        public PineappleConfig() {
            super("pineapple_psychic");
            init();
        }

        @Override
        public void init() {
            testBoolean = this.getBoolean("testBooleanConfig", testBoolean);

            this.build();
        }
    }

    private static final ModClassLoader modClassLoader = (ModClassLoader) Loader.instance().getModClassLoader();

    private static void autoRegisterTileEntity() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        ImmutableList<Object> knownLibraries = ImmutableList.builder().addAll(modClassLoader.getDefaultLibraries()).addAll(CoreModManager.getLoadedCoremods()).build();
        File[] minecraftSources = modClassLoader.getParentSources();
        HashSet<String> searchedSources = Sets.newHashSet();

        for (File minecraftSource : minecraftSources) {
            if (searchedSources.contains(minecraftSource.getAbsolutePath())) continue;
            searchedSources.add(minecraftSource.getAbsolutePath());
            if (minecraftSource.isFile()) {
                if (knownLibraries.contains(minecraftSource.getName())) continue;
                readFromZipFile(minecraftSource);
            }
            if (!minecraftSource.isDirectory()) continue;
            readFromDirectory(minecraftSource, minecraftSource);
        }
    }

    private static void readFromZipFile(File file) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        FileInputStream fileinputstream = new FileInputStream(file);
        ZipInputStream zipinputstream = new ZipInputStream(fileinputstream);

        while(true) {
            ZipEntry zipentry = zipinputstream.getNextEntry();
            if (zipentry == null) break;

            String fullName = zipentry.getName().replace('\\', '/');
            int pos = fullName.lastIndexOf(47);
            String name = pos == -1 ? fullName : fullName.substring(pos + 1);
            if (zipentry.isDirectory()) continue;

            String classname = name.replace(".class", "").replace("\\", ".").replace("/", ".");
            Class<?> clazz = Class.forName(classname, true, modClassLoader);
            if (!clazz.isAnnotationPresent(AutoRegistryTileEntity.class)) return;
            Object tile = clazz.newInstance();
            if (!(tile instanceof TileEntity)) return;
            GameRegistry.registerTileEntity((Class<? extends TileEntity>) clazz, clazz.getName());
        }
        fileinputstream.close();
    }

    private static void readFromDirectory(File directory, File file) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        File[] files = directory.listFiles();

        if (files == null) return;
        for (File child : files) {
            if (child.isDirectory()) readFromDirectory(child, file);
            else if (child.isFile()) {

                String classname = (!file.isFile() && child.getPath().startsWith(file.getPath()) ? child.getPath().substring(file.getPath().length() + 1) : "").replace(".class", "").replace("\\", ".").replace("/", ".");
                Class<?> clazz = Class.forName(classname, true, modClassLoader);
                if (!clazz.isAnnotationPresent(AutoRegistryTileEntity.class)) return;
                Object tile = clazz.newInstance();
                if (!(tile instanceof TileEntity)) return;
                GameRegistry.registerTileEntity((Class<? extends TileEntity>) clazz, clazz.getName());
            }
        }
    }}















