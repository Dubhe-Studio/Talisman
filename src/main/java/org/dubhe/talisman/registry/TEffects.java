package org.dubhe.talisman.registry;

import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.dubhe.talisman.ModInitializer;

public class TEffects {
    private static final DeferredRegister<Effect> EFFECT = DeferredRegister.create(ForgeRegistries.POTIONS, ModInitializer.MODID);

    public static final RegistryObject<Effect> BURNING = EFFECT.register("burning", () -> new TEffect(EffectType.HARMFUL, 16060178) {
        @Override
        public void performEffect(LivingEntity entity, int amplifier) {
            entity.attackEntityFrom(DamageSource.ON_FIRE, 1.0F);
            if (!entity.world.isRemote)
                ((ServerWorld)entity.world).spawnParticle(ParticleTypes.FLAME, entity.getPosX(), entity.getPosY(), entity.getPosZ(), 20, 0.2, 0.5, 0.2, 0);
        }

        @Override
        public boolean isReady(int duration, int amplifier) {
            int j = 20 >> amplifier;
            if (j > 0) {
                return duration % j == 0;
            } else {
                return true;
            }
        }
    });
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
