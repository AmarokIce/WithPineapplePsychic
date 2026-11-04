package club.someoneice.pineapplepsychic.core;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModClassLoader;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = PPcMain.MODID, name = PPcMain.NAME, version = PPcMain.VERSION, useMetadata = true)
public class PPcMain {
  public static final String MODID = "pineapple_psychic";
  public static final String NAME = "Pineapple Psychic";
  public static final String VERSION = "@VERSION@";
  public static final Logger LOGGER = LogManager.getLogger(PPcMain.NAME);
  public static final ModClassLoader modClassLoader =
      (ModClassLoader) Loader.instance().getModClassLoader();
  
  @Mod.Instance
  public static PPcMain INSTANCE;

  @Mod.EventHandler
  public static void onPreInitialization(FMLPreInitializationEvent event) {
    Lists.newArrayList(
            "",
            "====================================================================================================",
            "|| .---.  _                                .-.             .---.                  .-.    _        ||",
            "|| : .; ::_;                               : :             : .; :                 : :   :_;       ||",
            "|| :  _.'.-.,-.,-. .--.  .--.  .---. .---. : :   .--.      :  _.'.--. .-..-. .--. : `-. .-. .--.  ||",
            "|| : :   : :: ,. :' '_.'' .; ; : .; `: .; `: :_ ' '_.'     : :  `._-.': :; :'  ..': .. :: :'  ..' ||",
            "|| :_;   :_;:_;:_;`.__.'`.__,_;: ._.': ._.'`.__;`.__.'     :_;  `.__.'`._. ;`.__.':_;:_;:_;`.__.' ||",
            "||                             : :   : :                               .-. :                      ||",
            "||                             :_;   :_;                               `._.'                      ||",
            "====================================================================================================",
            "")
        .forEach(LOGGER::info);
  }

  @Mod.EventHandler
  public static void onInitialization(FMLPreInitializationEvent event) {}

  @Mod.EventHandler
  public static void onServerInitialization(FMLServerStartingEvent event) {}
}
