package club.someoneice.pineapplepsychic;

import club.someoneice.pineapplepsychic.api.IPineappleConfig;
import club.someoneice.pineapplepsychic.command.CommandSetConfig;
import club.someoneice.pineapplepsychic.command.CommandSetNBT;
import club.someoneice.pineapplepsychic.config.ConfigBeanV2;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = PineappleMain.MODID, version = PineappleMain.VERSION)
public class PineappleMain {
    public static final String MODID = "pineapple_psychic";
    public static final String VERSION = "1.4.1";
    public static final Logger LOGGER = LogManager.getLogger("[Pineapple Psychic]");

    @Mod.EventHandler
    public static void onPreInitialization(FMLPreInitializationEvent event) {
        LOGGER.info("Pineapple Psychic is loading now, the version: {} . Thanks for use!", VERSION);

        new PineappleConfig();
    }

    @Mod.EventHandler
    public static void onInitialization(FMLPreInitializationEvent event) {
    }

    @Mod.EventHandler
    public static void onServerInitialization(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandSetNBT());
        event.registerServerCommand(new CommandSetConfig());
    }

    public static class PineappleConfig extends ConfigBeanV2 implements IPineappleConfig {
        public PineappleConfig() {
            super("pineapple_psychic");
            init();
        }

        @Override
        public void init() {
        }
    }
}
