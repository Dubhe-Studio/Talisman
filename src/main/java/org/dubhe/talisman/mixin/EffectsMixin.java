package org.dubhe.talisman.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import org.dubhe.talisman.registry.TEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Effect.class)
public class EffectsMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;heal(F)V"), method = "performEffect", cancellable = true)
    public void point(LivingEntity entity, int amplifier, CallbackInfo info) {
        if (entity.getActivePotionEffect(TEffects.ANTIDOTE.get()) != null) info.cancel();
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;heal(F)V"), method = "affectEntity", cancellable = true)
    public void point(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity entity, int amplifier, double health, CallbackInfo info) {
        if (entity.getActivePotionEffect(TEffects.ANTIDOTE.get()) != null) info.cancel();
    }
}
