package club.someoneice.pineapplepsychic.api;

import club.someoneice.pineapplepsychic.core.AutoRender;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

@Deprecated
public interface IModelRender {
    /**
     * If you want to render a item (or block on hand) with obj, use this interface and registry with @AutoRender
     *
     * @see AutoRender
     */
    default void startRender(ResourceLocation texture, IModelCustom model) {
        if (this instanceof Item) {
            MinecraftForgeClient.registerItemRenderer((Item) this, new ItemRenderBase(texture, model));
        }
    }

    class ItemRenderBase implements IItemRenderer {
        private final ResourceLocation texture;
        private final IModelCustom model;


        public ItemRenderBase(ResourceLocation texture, IModelCustom model) {
            this.model = model;
            this.texture = texture;
        }

        @Override
        public boolean handleRenderType(ItemStack item, ItemRenderType type) {
            return true;
        }

        @Override
        public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
            return true;
        }

        @Override
        public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            GL11.glScalef(1.2F, 1.2F, 1.2F);
            (FMLClientHandler.instance().getClient()).renderEngine.bindTexture(texture);
            model.renderAll();
            GL11.glPopMatrix();
        }
    }
}
