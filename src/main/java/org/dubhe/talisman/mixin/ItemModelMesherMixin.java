package org.dubhe.talisman.mixin;

import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import org.dubhe.talisman.item.IWithCustomModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemModelMesher.class)
public abstract class ItemModelMesherMixin {

    @Shadow
    public abstract ModelManager getModelManager();

    @Inject(at = @At("HEAD"), method = "getItemModel(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/client/renderer/model/IBakedModel;", cancellable = true)
    private void getItemModel(ItemStack stack, CallbackInfoReturnable<IBakedModel> info) {
        CompoundNBT nbt = stack.getTag();
        if (stack.getItem() instanceof IWithCustomModel && (nbt == null || !nbt.contains("CustomModelData"))) {
            IBakedModel value = ((IWithCustomModel) stack.getItem()).getModel(stack, this.getModelManager());
            if (value != null) info.setReturnValue(value);
        }
    }
}
