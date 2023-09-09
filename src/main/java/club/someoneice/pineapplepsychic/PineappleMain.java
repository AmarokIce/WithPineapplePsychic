package club.someoneice.pineapplepsychic;

import club.someoneice.pineapplepsychic.command.CommandSetConfig;
import club.someoneice.pineapplepsychic.command.CommandSetNBT;
import club.someoneice.pineapplepsychic.command.IPineappleConfig;
import club.someoneice.pineapplepsychic.config.ConfigBean;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = "pineapple_psychic", version = "1.1.1")
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
        event.registerServerCommand(new CommandSetNBT());
        event.registerServerCommand(new CommandSetConfig());
    }

    public static class PineappleConfig implements IPineappleConfig {
        public static boolean usePineappleEvent;

        public PineappleConfig() {
            init();
        }

        @Override
        public void init() {
            ConfigBean cfg = new ConfigBean("PineappleConfig", this);
            usePineappleEvent = cfg.getBoolean("usePineappleEvent", true);
            cfg.build();
        }
    }
}
