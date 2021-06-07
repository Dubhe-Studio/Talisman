package org.dubhe.talisman.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import org.dubhe.talisman.ModInitializer;
import org.dubhe.talisman.TalismanUtils;
import org.dubhe.talisman.entities.TalismanEntity;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;

@SuppressWarnings("NullableProblems")
public class TalismanItem extends Item implements WithDefaultNbt {

    public TalismanItem(Properties properties) {
        super(properties);
    }

    public TalismanEntity createEntity(World world, PlayerEntity player, ItemStack stack) {
        return new TalismanEntity(world, player.getPositionVec(), player, TalismanUtils.toCollection(stack.getOrCreateTag().getList("executes", 8)));
    }

    @Override
    public CompoundNBT defaultNbt(CompoundNBT nbt) {
        nbt.putBoolean("throwable", true);
        nbt.putInt("needExperiences", 0);
        nbt.put("executes", new CompoundNBT());
        return nbt;
    }

    @Override
    public void onUse(World world, LivingEntity entity, ItemStack stack, int count) {
        boolean throwable = stack.getOrCreateTag().getBoolean("throwable");
        int needExperiences = stack.getOrCreateTag().getInt("needExperiences");
        Collection<String> executes = TalismanUtils.toCollection(stack.getOrCreateTag().getList("executes", 8));

        // 未完待续。。。困了。。。

    }
}
