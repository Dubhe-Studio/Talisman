package org.dubhe.talisman.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.dubhe.talisman.entity.TalismanEntity;
import org.dubhe.talisman.registry.event.TServerTickEvent;
import org.dubhe.talisman.talisman.AbstractTalisman;
import org.dubhe.talisman.talisman.Talismans;

import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings("NullableProblems")
public class TalismanItem extends Item implements IWithDefaultNbt {
    private final boolean throwable;
    @Nullable
    private final AbstractTalisman execute;

    public TalismanItem(Properties properties) {
        super(properties.rarity(Rarity.UNCOMMON));
        this.throwable = true;
        this.execute = null;
    }

    public TalismanItem(boolean throwable, @Nullable AbstractTalisman execute, Properties properties) {
        super(properties.rarity(Rarity.UNCOMMON));
        this.throwable = throwable;
        this.execute = execute;
    }

    @Override
    public CompoundNBT defaultNbt(CompoundNBT nbt) {
        nbt.putBoolean("throwable", this.throwable);
        ListNBT listNBT = new ListNBT();
        if (this.execute != null) listNBT.add(StringNBT.valueOf(this.execute.getName()));
        nbt.put("executes", listNBT);
        return nbt;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        CompoundNBT nbt = stack.getOrCreateTag();
        tooltip.add(new TranslationTextComponent("tooltip.talisman.throwable"+ (nbt.getBoolean("throwable") ? "" : ".no")));
        tooltip.add(new TranslationTextComponent("tooltip.talisman.execute"));
        ListNBT list = nbt.getList("executes", 8);
        if (!list.isEmpty()) {
            for (INBT execute : list) {
                String name = execute.getString();
                if (name.startsWith("function:"))
                    tooltip.add(new TranslationTextComponent("tooltip.talisman.execute.value", new TranslationTextComponent("text.talisman.function", name.split(":", 2)[1])));
                else {
                    IFormattableTextComponent value = new TranslationTextComponent("text.talisman." + name);
                    AbstractTalisman talisman = Talismans.get(name);
                    if (talisman != null) value.mergeStyle(talisman.isBeneficial() ? TextFormatting.GREEN : TextFormatting.RED);
                    tooltip.add(new TranslationTextComponent("tooltip.talisman.execute.value", value));
                }
            }
        }else tooltip.add(new TranslationTextComponent("tooltip.talisman.execute.no"));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack item = player.getHeldItem(hand);
        boolean throwable = item.getOrCreateTag().getBoolean("throwable");
        if (throwable) {
            TalismanEntity talisman = this.createEntity(world, player, item);
            world.addEntity(talisman);
        }else {
            TServerTickEvent.addExecute(world, player, item.getOrCreateTag().getList("executes", 8), null);
        }
        if (!player.isCreative()) item.shrink(1);
        player.addStat(Stats.ITEM_USED.get(this));
        return ActionResult.resultSuccess(item);
    }

    public TalismanEntity createEntity(World world, PlayerEntity player, ItemStack stack) {
        return new TalismanEntity(world, player.getEyePosition(1.0F).add(0, -0.525D, 0), player, stack.getOrCreateTag().getList("executes", 8));
    }
}
