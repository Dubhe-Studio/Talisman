package org.dubhe.talisman.registry;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.dubhe.talisman.ModInitializer;

public class TEffects {
    private static final DeferredRegister<Effect> EFFECT = DeferredRegister.create(ForgeRegistries.POTIONS, ModInitializer.MODID);

    public static final RegistryObject<Effect> BURNING = EFFECT.register("burning", () -> new TEffect(EffectType.HARMFUL, 16060178));
    public static final RegistryObject<Effect> ANTIDOTE = EFFECT.register("antidote", () -> new TEffect(EffectType.HARMFUL, 7047999));

    private static class TEffect extends Effect {
        protected TEffect(EffectType type, int color) {
            super(type, color);
        }
    }

    public static void register(IEventBus bus) {
        EFFECT.register(bus);
    }
}
