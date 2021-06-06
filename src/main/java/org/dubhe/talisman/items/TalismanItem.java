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

    private boolean throwable;
    private int needExperiences;
    private final Collection<String> execute;

    public TalismanItem() {
        this(true, 0, null);
    }

    public TalismanItem(boolean throwable, int needExperiences, @Nullable Collection<String> execute) {
        super(new Properties().group(ModInitializer.TalismanItemGroup));
        this.throwable = throwable;
        this.needExperiences = needExperiences;
        this.execute = execute == null ? Collections.emptyList() : execute;
    }

    public TalismanEntity createEntity(World world, PlayerEntity player) {
        return new TalismanEntity(world, player.getPositionVec(), player, this.execute);
    }

    @Override
    public CompoundNBT defaultNbt(CompoundNBT nbt) {
        nbt.putBoolean("throwable", this.throwable);
        nbt.putInt("needExperiences", this.needExperiences);
        nbt.put("executes", TalismanUtils.toListNBT(this.execute));
        return nbt;
    }

    @Override
    public void onUse(World world, LivingEntity entity, ItemStack stack, int count) {
        boolean throwable = stack.getOrCreateTag().getBoolean("throwable");
        int needExperiences = stack.getOrCreateTag().getInt("needExperiences");
        Collection<String> executes = stack.getOrCreateTag().contains("executes") ? TalismanUtils.toCollection(stack.getOrCreateTag().getList("executes", 8)) : Collections.emptyList();

        // 未完待续。。。困了。。。

    }
}
