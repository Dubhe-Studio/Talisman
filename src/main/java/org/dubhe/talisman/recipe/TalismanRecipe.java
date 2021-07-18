package org.dubhe.talisman.recipe;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.dubhe.talisman.registry.TRecipes;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@SuppressWarnings("NullableProblems")
public class TalismanRecipe implements IRecipe<CraftingInventory> {
    private final int recipeWidth;
    private final int recipeHeight;
    private final NonNullList<Ingredient> recipeItems;
    private final OutputAndDemand outputAndDemand;
    private final ResourceLocation id;

    public TalismanRecipe(ResourceLocation id, int recipeWidth, int recipeHeight, NonNullList<Ingredient> recipeItems, OutputAndDemand outputAndDemand) {
        this.id = id;
        this.recipeWidth = recipeWidth;
        this.recipeHeight = recipeHeight;
        this.recipeItems = recipeItems;
        this.outputAndDemand = outputAndDemand;
    }

    @Override
    public boolean matches(CraftingInventory inv, World world) {
        for(int i = 0; i <= inv.getWidth() - this.recipeWidth; ++i) {
            for(int j = 0; j <= inv.getHeight() - this.recipeHeight; ++j) {
                if (this.checkMatch(inv, i, j, true)) {
                    return true;
                }

                if (this.checkMatch(inv, i, j, false)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkMatch(CraftingInventory craftingInventory, int width, int height, boolean p_77573_4_) {
        for(int i = 0; i < craftingInventory.getWidth(); ++i) {
            for(int j = 0; j < craftingInventory.getHeight(); ++j) {
                int k = i - width;
                int l = j - height;
                Ingredient ingredient = Ingredient.EMPTY;
                if (k >= 0 && l >= 0 && k < this.recipeWidth && l < this.recipeHeight) {
                    if (p_77573_4_) {
                        ingredient = this.recipeItems.get(this.recipeWidth - k - 1 + l * this.recipeWidth);
                    } else {
                        ingredient = this.recipeItems.get(k + l * this.recipeWidth);
                    }
                }

                if (!ingredient.test(craftingInventory.getStackInSlot(i + j * craftingInventory.getWidth()))) {
                    return false;
                }
            }
        }
        return true;
    }

    public OutputAndDemand getOutput() {
        return this.outputAndDemand.copy();
    }

    /**
     * please use {@link TalismanRecipe#getOutput }
     */
    @Override
    @Deprecated
    public ItemStack getCraftingResult(CraftingInventory inv) {
        return this.getRecipeOutput().copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return width >= this.recipeWidth && height >= this.recipeHeight;
    }

    /**
     * please use {@link TalismanRecipe#getOutput }
     */
    @Override
    @Deprecated
    public ItemStack getRecipeOutput() {
        return this.outputAndDemand.getItemStack();
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return TRecipes.TALISMAN_CRAFTING_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return TRecipes.TALISMAN_CRAFTING_TYPE;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.recipeItems;
    }

    public static Map<String, Ingredient> deserializeKey(JsonObject json) throws CommandSyntaxException {
        Map<String, Ingredient> map = Maps.newHashMap();

        for(Map.Entry<String, JsonElement> entry : json.entrySet()) {
            if (entry.getKey().length() != 1) {
                throw new JsonSyntaxException("Invalid key entry: '" + entry.getKey() + "' is an invalid symbol (must be 1 character only).");
            }

            if (" ".equals(entry.getKey())) {
                throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
            }

            Ingredient.IItemList list = deserializeItemList(entry.getValue().getAsJsonObject());
            Ingredient ingredient = list.getStacks().size() == 0 ? Ingredient.EMPTY : new Ingredient(Stream.of(list));

            map.put(entry.getKey(), ingredient);
        }

        map.put(" ", Ingredient.EMPTY);
        return map;
    }

    public static Ingredient.IItemList deserializeItemList(JsonObject json) throws CommandSyntaxException {
        if (json.has("item") && json.has("tag")) {
            throw new JsonParseException("An ingredient entry is either a tag or an item, not both");
        } else if (json.has("item")) {
            ResourceLocation resourcelocation = new ResourceLocation(JSONUtils.getString(json, "item"));
            Item item = Optional.ofNullable(ForgeRegistries.ITEMS.getValue(resourcelocation)).orElseThrow(() -> new JsonSyntaxException("Unknown item '" + resourcelocation + "'"));
            CompoundNBT nbt = json.has("nbt") ? JsonToNBT.getTagFromJson(JSONUtils.getString(json, "nbt")) : null;
            return new Ingredient.SingleItemList(new ItemStack(item, 1, nbt));
        } else if (json.has("tag")) {
            ResourceLocation resourcelocation = new ResourceLocation(JSONUtils.getString(json, "tag"));
            ITag<Item> tag = TagCollectionManager.getManager().getItemTags().get(resourcelocation);
            if (tag == null) {
                throw new JsonSyntaxException("Unknown item tag '" + resourcelocation + "'");
            } else {
                return new Ingredient.TagList(tag);
            }
        } else {
            throw new JsonParseException("An ingredient entry needs either a tag or an item");
        }
    }

    @SuppressWarnings("ConstantConditions")
    private static OutputAndDemand deserializeItem(JsonObject json) throws CommandSyntaxException {
        String id = JSONUtils.getString(json, "item");
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(id));
        if (item == null) throw new JsonSyntaxException("Unknown item '" + id + "'");
        if (json.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            int count = JSONUtils.getInt(json, "count", 1);
            boolean throwable = JSONUtils.getBoolean(json, "throwable", true);
            int experience = JSONUtils.getInt(json, "experience", 0);
            JsonArray array = JSONUtils.getJsonArray(json, "executes", new JsonArray());
            int cmd = JSONUtils.getInt(json, "custom_model_data", -1);
            String display = JSONUtils.getString(json, "display", "");

            ItemStack stack = new ItemStack(item, count);
            stack.getOrCreateTag().putBoolean("throwable", throwable);
            if (cmd != -1) stack.getOrCreateTag().putInt("custom_model_data", cmd);
            ListNBT listNBT = new ListNBT();
            for (JsonElement jsonElement : array) {
                listNBT.add(StringNBT.valueOf(jsonElement.getAsString()));
            }
            stack.getOrCreateTag().put("executes", listNBT);
            if (!"".equals(display)) {
                stack.getOrCreateTag().put("display", JsonToNBT.getTagFromJson(display));
            }

            return new OutputAndDemand(stack, experience);
        }
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<TalismanRecipe> {

        @Override
        public TalismanRecipe read(ResourceLocation recipeId, JsonObject json) {
            try {
                Map<String, Ingredient> keys = TalismanRecipe.deserializeKey(JSONUtils.getJsonObject(json, "key"));
                String[] patterns = ShapedRecipe.shrink(ShapedRecipe.patternFromJson(JSONUtils.getJsonArray(json, "pattern")));
                int width = patterns[0].length();
                int height = patterns.length;
                NonNullList<Ingredient> ingredientList = ShapedRecipe.deserializeIngredients(patterns, keys, width, height);
                OutputAndDemand output;
                output = TalismanRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
                return new TalismanRecipe(recipeId, width, height, ingredientList, output);
            } catch (CommandSyntaxException e) {
                throw new RuntimeException(e.getMessage(), e.getCause());
            }
        }

        @Nullable
        @Override
        public TalismanRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            int width = buffer.readVarInt();
            int height = buffer.readVarInt();
            NonNullList<Ingredient> ingredientList = NonNullList.withSize(width * height, Ingredient.EMPTY);

            for(int i = 0; i < ingredientList.size(); ++i) {
                ingredientList.set(i, Ingredient.read(buffer));
            }

            ItemStack itemstack = buffer.readItemStack();
            int experience = buffer.readVarInt();
            OutputAndDemand output = new OutputAndDemand(itemstack, experience);
            return new TalismanRecipe(recipeId, width, height, ingredientList, output);
        }

        @Override
        public void write(PacketBuffer buffer, TalismanRecipe recipe) {
            buffer.writeVarInt(recipe.recipeWidth);
            buffer.writeVarInt(recipe.recipeHeight);

            for(Ingredient ingredient : recipe.recipeItems) {
                ingredient.write(buffer);
            }

            buffer.writeItemStack(recipe.outputAndDemand.getItemStack());
            buffer.writeVarInt(recipe.outputAndDemand.getExperience());
        }
    }

}
