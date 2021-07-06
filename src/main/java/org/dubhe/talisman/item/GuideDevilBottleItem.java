package org.dubhe.talisman.item;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

@SuppressWarnings("NullableProblems")
public class GuideDevilBottleItem extends Item {
    public GuideDevilBottleItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack item = player.getHeldItem(hand);
        if (!world.isRemote) {
            if (player.experienceLevel > 1 || player.experience >= 5) {
                ItemStack bottle = new ItemStack(Items.EXPERIENCE_BOTTLE, 1);
                if (player.inventory.addItemStackToInventory(bottle)) {
                    bottle.setCount(1);
                    ItemEntity itementity = player.dropItem(bottle, false);
                    if (itementity != null) itementity.makeFakeItem();
                    player.world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((player.getRNG().nextFloat() - player.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    player.container.detectAndSendChanges();
                } else {
                    ItemEntity itementity = player.dropItem(bottle, false);
                    if (itementity != null) {
                        itementity.setNoPickupDelay();
                        itementity.setOwnerId(player.getUniqueID());
                    }
                }
                item.shrink(1);
                player.addStat(Stats.ITEM_USED.get(this));
                return ActionResult.resultSuccess(item);
            }
        }
        return ActionResult.resultConsume(item);
    }
}
