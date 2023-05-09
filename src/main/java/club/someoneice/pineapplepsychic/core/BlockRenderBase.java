package club.someoneice.pineapplepsychic.core;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class BlockRenderBase extends TileEntitySpecialRenderer {
    public static ResourceLocation texture;
    private final Class<? extends TileEntity> tileClazz;
    private final Block block;
    IModelCustom model;


    public BlockRenderBase(String modid, ResourceLocation texturePath, ResourceLocation objModelPath, Class<? extends TileEntity> tile, Block block) {
        texture = texturePath;
        this.tileClazz = tile;
        this.block = block;

        this.model = AdvancedModelLoader.loadModel(objModelPath);

        initRender();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double posX, double posY, double posZ, float f) {
        // bindTexture(texture);

        GL11.glPushMatrix();
        GL11.glTranslated(posX + 0.5, posY + 0.5, posZ + 0.5);
        GL11.glScalef(1.0f, 1.0f, 1.0f);
        GL11.glRotatef(1.0f, 0F, 1F, 0.5F);
        (FMLClientHandler.instance().getClient()).renderEngine.bindTexture(texture);
        this.model.renderAll();
        GL11.glPopMatrix();
    }

    @SideOnly(Side.CLIENT)
    public void initRender() {
        ClientRegistry.bindTileEntitySpecialRenderer(tileClazz, this);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(block), new ItemRenderBase(this));
    }

    private class ItemRenderBase implements IItemRenderer {
        private final BlockRenderBase render;

        public ItemRenderBase(BlockRenderBase base) {
            this.render = base;
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
            render.model.renderAll();
            GL11.glPopMatrix();
        }
    }
}
