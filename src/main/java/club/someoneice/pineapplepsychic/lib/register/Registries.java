package club.someoneice.pineapplepsychic.lib.register;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class Registries {
  public static final DataRegister<Item> ITEMS = new DataRegister<>(Registries::registerItem);
  public static final DataRegister<Block> BLOCKS = new DataRegister<>(Registries::registerBlock);

  public static void registerItem(final ResourceLocation location, final Item item) {
    GameRegistry.registerItem(item, location.getResourcePath(), location.getResourceDomain());
  }

  public static void registerBlock(final ResourceLocation location, final Block block) {
    GameRegistry.registerBlock(block, location.getResourcePath());
  }
}
