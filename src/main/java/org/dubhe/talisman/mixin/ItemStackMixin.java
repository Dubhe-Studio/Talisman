package org.dubhe.talisman.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IItemProvider;
import org.dubhe.talisman.item.WithDefaultNbt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow
    public abstract CompoundNBT getOrCreateTag();

    @SuppressWarnings("ConstantConditions")
    @Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/util/IItemProvider;ILnet/minecraft/nbt/CompoundNBT;)V")
    private void init(IItemProvider item, int count, @Nullable CompoundNBT capNbt, CallbackInfo info) {
        if (item != null && item.asItem() instanceof WithDefaultNbt) {
            CompoundNBT nbt = this.getOrCreateTag();
            CompoundNBT newNbt =((WithDefaultNbt)item.asItem()).defaultNbt(new CompoundNBT());
            for (String s : newNbt.keySet()) {
                if (!nbt.contains(s)) nbt.put(s, newNbt.get(s));
            }
        }
    }
}
