package org.dubhe.talisman.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.dubhe.talisman.entities.TalismanEntity;

import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings("NullableProblems")
public class TalismanItem extends Item implements WithDefaultNbt {

    public TalismanItem(Properties properties) {
        super(properties.rarity(Rarity.UNCOMMON));
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
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack item = player.getHeldItem(hand);
        boolean throwable = item.getOrCreateTag().getBoolean("throwable");
        int needExperiences = item.getOrCreateTag().getInt("needExperiences");
        if (player.isCreative() || player.experienceTotal >= needExperiences) {
            TalismanEntity talisman = this.createEntity(world, player, item, throwable);
            world.addEntity(talisman);
            if (!player.isCreative()) item.shrink(1);
            player.addStat(Stats.ITEM_USED.get(this));
            return ActionResult.resultSuccess(item);
        }
        return ActionResult.resultFail(item);
    }

    public TalismanEntity createEntity(World world, PlayerEntity player, ItemStack stack, boolean throwable) {
        return new TalismanEntity(world, player.getPositionVec(), player, stack.getOrCreateTag().getList("executes", 8), throwable);
    }

}