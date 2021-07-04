package org.dubhe.talisman.item;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.dubhe.talisman.entity.TalismanEntity;
import org.dubhe.talisman.talisman.AbstractTalisman;
import org.dubhe.talisman.talisman.Talismans;

import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings("NullableProblems")
public class TalismanItem extends Item implements IWithDefaultNbt, IWithCustomModel {

    public TalismanItem(Properties properties) {
        super(properties.rarity(Rarity.UNCOMMON));
    }

    @Override
    public CompoundNBT defaultNbt(CompoundNBT nbt) {
        nbt.putBoolean("throwable", true);
        nbt.put("executes", new ListNBT());
        return nbt;
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        ListNBT list = stack.getOrCreateTag().getList("executes", 8);
        if (list.size() == 1 && !list.getString(0).startsWith("function:"))
            return String.format("item.talisman.%s_talisman", list.getString(0));
        return super.getTranslationKey(stack);
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
                else
                    tooltip.add(new TranslationTextComponent("tooltip.talisman.execute.value", new TranslationTextComponent("text.talisman." + name)));
            }
        }else tooltip.add(new TranslationTextComponent("tooltip.talisman.execute.no"));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack item = player.getHeldItem(hand);
        boolean throwable = item.getOrCreateTag().getBoolean("throwable");
        TalismanEntity talisman = this.createEntity(world, player, item, throwable);
        world.addEntity(talisman);
        if (!player.isCreative()) item.shrink(1);
        player.addStat(Stats.ITEM_USED.get(this));
        return ActionResult.resultSuccess(item);
    }

    public TalismanEntity createEntity(World world, PlayerEntity player, ItemStack stack, boolean throwable) {
        return new TalismanEntity(world, player.getEyePosition(1.0F).add(0, -0.525D, 0), player, stack.getOrCreateTag().getList("executes", 8), throwable);
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            items.add(new ItemStack(this));
            items.add(groupItem(this, true, Talismans.EXPLODE));
            items.add(groupItem(this, true, Talismans.TRANSFER));
            items.add(groupItem(this, true, Talismans.FIREBALL));
            items.add(groupItem(this, true, Talismans.IMMOBILITY));
            items.add(groupItem(this, true, Talismans.TREATMENT));
            items.add(groupItem(this, true, Talismans.PUPPET));
            items.add(groupItem(this, true, Talismans.SEPARATION));
            items.add(groupItem(this, true, Talismans.THUNDER));
            items.add(groupItem(this, true, Talismans.MUTE));
            items.add(groupItem(this, true, Talismans.CARRY));
            items.add(groupItem(this, true, Talismans.WATER_BALL));
            items.add(groupItem(this, true, Talismans.DOOM));
            items.add(groupItem(this, true, Talismans.HUGE_EXPLOSION));
            items.add(groupItem(this, false, Talismans.CHANGE_CLOTHING));
        }
    }

    private static ItemStack groupItem(Item item, boolean throwable, AbstractTalisman execute) {
        ItemStack stack = new ItemStack(item);
        stack.getOrCreateTag().putBoolean("throwable", throwable);
        ListNBT list = new ListNBT();
        list.add(StringNBT.valueOf(execute.getName()));
        stack.getOrCreateTag().put("executes", list);
        return stack;
    }

    @Override
    public IBakedModel getModel(ItemStack stack, ModelManager manager) {
        ListNBT executes = stack.getOrCreateTag().getList("executes", 8);
        if (executes.size() > 0 && !executes.getString(0).startsWith("function:")) {
            String type = executes.getString(0);
            IBakedModel model = manager.getModel(new ModelResourceLocation("talisman:talisman/" + type, "inventory"));
            System.out.println("talisman:talisman/" + type);
            return model;
        }
        return null;
    }
}
