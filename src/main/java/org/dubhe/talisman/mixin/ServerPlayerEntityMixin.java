package org.dubhe.talisman.mixin;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dubhe.talisman.registry.TItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.List;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
    private static final List<Item> RAOIST_ROBE = Lists.newArrayList(TItems.RAOIST_ROBE_BOOTS.get(), TItems.RAOIST_ROBE_LEGGINGS.get(), TItems.RAOIST_ROBE_CHESTPLATE.get(), TItems.RAOIST_ROBE_HELMET.get());
    private int time = 0;

    public ServerPlayerEntityMixin(World p_i241920_1_, BlockPos p_i241920_2_, float p_i241920_3_, GameProfile p_i241920_4_) {
        super(p_i241920_1_, p_i241920_2_, p_i241920_3_, p_i241920_4_);
    }

    @Inject(at = @At("HEAD"), method = "playerTick")
    private void tick(CallbackInfo info) {
        Iterator<ItemStack> list = this.getArmorInventoryList().iterator();
        Iterator<Item> armors = RAOIST_ROBE.iterator();
        for (int i = 0; i < 4; i++) {
            if (list.next().getItem() != armors.next()) {
                this.time = 0;
                break;
            }
            if (i == 3) this.time++;
        }
        if (this.time == 20) {
            this.giveExperiencePoints(1);
            this.time = 0;
        }
    }
}
