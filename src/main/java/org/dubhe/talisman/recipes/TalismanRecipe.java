package org.dubhe.talisman.recipes;

import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.dubhe.talisman.registry.RecipeRegistry;

import javax.annotation.Nullable;

@SuppressWarnings("NullableProblems")
public class TalismanRecipe implements IRecipe<IInventory> {
    static int MAX_WIDTH = 3;
    static int MAX_HEIGHT = 3;

    public static void setCraftingSize(int width, int height) {
        if (MAX_WIDTH < width) MAX_WIDTH = width;
        if (MAX_HEIGHT < height) MAX_HEIGHT = height;
    }

    private final int recipeWidth;
    private final int recipeHeight;
    private final NonNullList<Ingredient> recipeItems;
    private final ItemStack recipeOutput;
    private final ResourceLocation id;
    private final String group;

    public TalismanRecipe(ResourceLocation id, String group, int recipeWidth, int recipeHeight, NonNullList<Ingredient> recipeItems, ItemStack recipeOutput) {
        this.id = id;
        this.group = group;
        this.recipeWidth = recipeWidth;
        this.recipeHeight = recipeHeight;
        this.recipeItems = recipeItems;
        this.recipeOutput = recipeOutput;
    }

    @Override
    public boolean matches(IInventory inv, World world) {
//        for(int i = 0; i <= inv.getWidth() - this.recipeWidth; ++i) {
//            for(int j = 0; j <= inv.getHeight() - this.recipeHeight; ++j) {
//                if (this.checkMatch(inv, i, j, true)) {
//                    return true;
//                }
//
//                if (this.checkMatch(inv, i, j, false)) {
//                    return true;
//                }
//            }
        return false;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return null;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width >= this.recipeWidth && height >= this.recipeHeight;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeRegistry.TALISMAN_CRAFTING.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return null;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.recipeItems;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<TalismanRecipe> {

        @Override
        public TalismanRecipe read(ResourceLocation recipeId, JsonObject json) {
            return null;
        }

        @Nullable
        @Override
        public TalismanRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            return null;
        }

        @Override
        public void write(PacketBuffer buffer, TalismanRecipe recipe) {

        }

    }

}
