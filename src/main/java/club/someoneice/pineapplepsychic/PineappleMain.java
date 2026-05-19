package club.someoneice.pineapplepsychic;

import club.someoneice.pineapplepsychic.core.util.HelperAutoTile;
import club.someoneice.pineapplepsychic.lib.event.handler.SPlayerEvent;
import club.someoneice.pineapplepsychic.lib.event.handler.STickEvent;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModClassLoader;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = PineappleMain.MODID, name = PineappleMain.NAME, version = PineappleMain.VERSION, useMetadata = true)
public class PineappleMain {
  public static final String MODID = "pineapple_psychic";
  public static final String NAME = "Pineapple Psychic";
  public static final String VERSION = "@VERSION@";
  public static final Logger LOGGER = LogManager.getLogger("[Pineapple Psychic]");
  public static final ModClassLoader modClassLoader = (ModClassLoader) Loader.instance().getModClassLoader();

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
      ""
    ).forEach(LOGGER::info);

    try {
      HelperAutoTile.register();
    } catch (Exception e) {
      LOGGER.error(e);
    }

    MinecraftForge.EVENT_BUS.register(new SPlayerEvent());
    MinecraftForge.EVENT_BUS.register(new STickEvent());
    FMLCommonHandler.instance().bus().register(new SPlayerEvent());
    FMLCommonHandler.instance().bus().register(new STickEvent());
  }

  @Mod.EventHandler
  public static void onInitialization(FMLPreInitializationEvent event) {
  }

  @Mod.EventHandler
  public static void onServerInitialization(FMLServerStartingEvent event) {
    // event.registerServerCommand(new CommandSetConfig());
  }


}















