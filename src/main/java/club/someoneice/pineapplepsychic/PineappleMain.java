package club.someoneice.pineapplepsychic;

import club.someoneice.pineapplepsychic.core.ConfigBean;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = "pineapple_psychic", version = "0.0.1")
public class PineappleMain {
    public static Logger LOGGER = LogManager.getLogger("[Pineapple Psychic]");

    @Mod.EventHandler
    public static void onPreInitialization(FMLPreInitializationEvent event) {
        new PineappleConfig();
    }

    @Mod.EventHandler
    public static void onInitialization(FMLPreInitializationEvent event) {
    }

    @Mod.EventHandler
    public static void onServerInitialization(FMLServerStartingEvent event) {
    }

    public static class PineappleConfig {
        public static boolean usePineappleEvent;

        public PineappleConfig() {
            ConfigBean cfg = new ConfigBean("PineappleConfig");
            usePineappleEvent = cfg.getBoolean("usePineappleEvent", true);
            cfg.build();
        }
    }
}
