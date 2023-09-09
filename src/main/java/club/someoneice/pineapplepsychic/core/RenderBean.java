package club.someoneice.pineapplepsychic.core;

import club.someoneice.pineapplepsychic.api.IModelRender;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderBean {
    protected String modid;
    protected String name;
    protected ResourceLocation texture;
    protected IModelCustom model;
    protected Block block;

    public IModelRender output;

    /**
     * The render mode you want to use. <br />
     * If you like, always use item you can.
     */
    enum ObjType {
        ITEM,
        BLOCK
    }
    public RenderBean(ObjType type, String modid, String name) {
        if (type == ObjType.ITEM) {
            this.output = new ItemBase();
            AutoRender.registryRenderObj((ItemBase) output, name, modid);
            output.startRender(texture, model);
        } else if (type == ObjType.BLOCK) {
            if (block == null) throw new NullPointerException("You cannot use render block without a block!");
            this.output = new BlockBase();
            AutoRender.registryRenderObj(block, ((BlockBase) output).getClass(), name);
            output.startRender(texture, model);
        }
    }

    /**
     * Set the obj texture.
     * @param texture The path of texture.
     */
    public RenderBean setTexture(ResourceLocation texture) {
        this.texture = texture;
        return this;
    }

    /**
     * Set the path of obj you want to render.
     * @param path The path of obj.
     */
    public RenderBean setModel(ResourceLocation path) {
        this.model = AdvancedModelLoader.loadModel(path);
        return this;
    }

    /**
     * Set a block if you set the type is BLOCK Render mode.
     * @param block The block you want to render.
     * @see ObjType
     */
    public RenderBean setBlock(Block block) {
        this.block = block;
        return this;
    }

    protected class ItemBase extends Item implements IModelRender {
        public ItemBase() {
            this.setUnlocalizedName(name);
            this.setTextureName(modid + ":" + name);
        }
    }

    protected class BlockBase extends ItemBlock implements IModelRender {
        public BlockBase() {
            super(block);
            this.setUnlocalizedName(name);
            this.setTextureName(modid + ":" + name);
        }
    }
}
