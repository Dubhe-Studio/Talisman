package org.dubhe.talisman.mixin;

import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.profiler.IProfiler;
import org.dubhe.talisman.registry.TModels;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelBakery.class)
public abstract class ModelBakeryMixin {

    @Shadow
    protected abstract void loadTopModel(ModelResourceLocation locationIn);

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/profiler/IProfiler;endStartSection(Ljava/lang/String;)V", args = "true", ordinal = 3), method = "processLoading")
    private void processLoading(IProfiler profilerIn, int maxMipmapLevel, CallbackInfo info) {
        for (TModels model : TModels.values()) {
            this.loadTopModel(new ModelResourceLocation(model.getNamespace(), "inventory"));
        }
    }
}
