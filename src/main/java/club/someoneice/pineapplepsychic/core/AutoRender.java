package club.someoneice.pineapplepsychic.core;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

@Deprecated
public class AutoRender {
    public static Item registryRenderObj(Item item, String name, String modid) {
        GameRegistry.registerItem(item, name, modid);
        return item;
    }

    public static Item registryRenderObj(Item item, String name) {
        GameRegistry.registerItem(item, name);
        return item;
    }

    public static Block registryRenderObj(Block block, Class<? extends ItemBlock> itemBlock,  String name) {
        GameRegistry.registerBlock(block, itemBlock, name);
        return block;
    }
}
