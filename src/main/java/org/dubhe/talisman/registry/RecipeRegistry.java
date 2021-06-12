package org.dubhe.talisman.registry;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.dubhe.talisman.ModInitializer;
import org.dubhe.talisman.recipes.TalismanRecipe;

public class RecipeRegistry {
    private static final DeferredRegister<IRecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ModInitializer.MODID);

    public static final RegistryObject<TalismanRecipe.Serializer> TALISMAN_CRAFTING = RECIPES.register("talisman_crafting", TalismanRecipe.Serializer::new);


    public static void completeRegistry(IEventBus bus) {
        RECIPES.register(bus);
    }



}
