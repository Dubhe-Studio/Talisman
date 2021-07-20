package org.dubhe.talisman.registry;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.dubhe.talisman.ModInitializer;
import org.dubhe.talisman.recipe.TalismanRecipe;

public class TRecipes {
    private static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ModInitializer.MODID);

    public static final RegistryObject<TalismanRecipe.Serializer> TALISMAN_CRAFTING_SERIALIZER = RECIPE_SERIALIZER.register("talisman_crafting", TalismanRecipe.Serializer::new);
    public static final IRecipeType<TalismanRecipe> TALISMAN_CRAFTING_TYPE = IRecipeType.register(ModInitializer.getIdentifier("talisman_crafting").toString());


    public static void register(IEventBus bus) {
        RECIPE_SERIALIZER.register(bus);
    }



}
