package club.someoneice.pineapplepsychic.core;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import java.util.List;

public abstract class RecipeBase implements IRecipe {
    private final ItemStack itemOutput;
    private final RecipeHandler handler;
    private int size;

    @SuppressWarnings("unchecked")
    public RecipeBase(RecipeHandler handler, ItemStack output) {
        this.handler = handler;
        this.itemOutput = output;
        ((List<IRecipe>) CraftingManager.getInstance().getRecipeList()).add(this);
    }

    @Override
    public boolean matches(InventoryCrafting inv, World world) {
        this.size = inv.getSizeInventory();
        ItemStack[] items = new ItemStack[this.size];
        for (int i = 0; i < this.size; i++) {
            items[i] = inv.getStackInSlot(i);
        }
        return this.handler.init(inv, items, world, this.itemOutput);
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return this.itemOutput.copy();
    }

    @Override
    public int getRecipeSize() {
        return this.size;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.itemOutput;
    }

    public final void registerRecipe() {
        CraftingManager.getInstance().getRecipeList().add(this);
    }

    public interface RecipeHandler {
        boolean init(InventoryCrafting crafting, ItemStack[] items, World world, ItemStack output);
    }
}
