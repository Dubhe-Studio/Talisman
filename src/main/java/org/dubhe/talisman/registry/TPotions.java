package org.dubhe.talisman.registry;

import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.dubhe.talisman.ModInitializer;

@SuppressWarnings("unused")
public class TPotions {
    private static final DeferredRegister<Potion> POTION = DeferredRegister.create(ForgeRegistries.POTION_TYPES, ModInitializer.MODID);

    public static final RegistryObject<Potion> BURNING_POTION = POTION.register("burning", () -> new Potion(new EffectInstance(TEffects.BURNING.get(), 380)));
    public static final RegistryObject<Potion> LONG_BURNING_POTION = POTION.register("long_burning", () -> new Potion(new EffectInstance(TEffects.BURNING.get(), 600)));
    public static final RegistryObject<Potion> STRONG_BURNING_POTION = POTION.register("strong_burning", () -> new Potion(new EffectInstance(TEffects.BURNING.get(), 182, 1)));
    public static final RegistryObject<Potion> ANTIDOTE_POTION = POTION.register("antidote", () -> new Potion(new EffectInstance(TEffects.ANTIDOTE.get(), 900)));
    public static final RegistryObject<Potion> LONG_ANTIDOTE_POTION = POTION.register("long_antidote", () -> new Potion(new EffectInstance(TEffects.ANTIDOTE.get(), 1800)));


    public static void register(IEventBus bus) {
        POTION.register(bus);
    }
}
