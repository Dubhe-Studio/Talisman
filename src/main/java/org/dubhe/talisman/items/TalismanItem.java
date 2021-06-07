package org.dubhe.talisman.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.dubhe.talisman.ModInitializer;
import org.dubhe.talisman.TalismanUtils;
import org.dubhe.talisman.entities.TalismanEntity;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("NullableProblems")
public class TalismanItem extends Item implements WithDefaultNbt {

    public TalismanItem(Properties properties) {
        super(properties.rarity(Rarity.UNCOMMON));
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
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        CompoundNBT nbt = stack.getOrCreateTag();
        tooltip.add(new TranslationTextComponent("tooltip.talisman.throwable"+ (nbt.getBoolean("throwable") ? "" : ".no")));
        tooltip.add(new TranslationTextComponent("tooltip.talisman.needExperiences", new TranslationTextComponent("text.all", nbt.getInt("needExperiences")).mergeStyle(TextFormatting.GREEN)));
        tooltip.add(new TranslationTextComponent("tooltip.talisman.execute"));
        ListNBT list = nbt.getList("executes", 8);
        if (!list.isEmpty()) {
            for (INBT execute : list) {
                tooltip.add(new TranslationTextComponent("tooltip.talisman.execute.value", execute));
            }
        }else tooltip.add(new TranslationTextComponent("tooltip.talisman.execute.no"));
    }

    @Override
    public void onUse(World world, LivingEntity entity, ItemStack stack, int count) {
        boolean throwable = stack.getOrCreateTag().getBoolean("throwable");
        int needExperiences = stack.getOrCreateTag().getInt("needExperiences");
        Collection<String> executes = TalismanUtils.toCollection(stack.getOrCreateTag().getList("executes", 8));





        // 未完待续。。。困了。。。

    }
}
