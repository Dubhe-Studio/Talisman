package org.dubhe.talisman.talisman;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dubhe.talisman.container.ChangeClothingContainer;
import org.dubhe.talisman.item.ChangeClothingTalismanItem;

import javax.annotation.Nullable;

public class ChangeClothingTalisman extends AbstractTalisman {
    private static final Logger LOGGER = LogManager.getLogger();
    protected ChangeClothingTalisman() {
        super("change_clothing", true);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void execute(Entity entity, Vector3d pos, @Nullable Hand hand, @Nullable LivingEntity target) {
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) entity;
            ItemStack item = player.getHeldItem(hand);
            if (player.isSneaking()) {
                NetworkHooks.openGui(player, new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return new TranslationTextComponent("item.talisman.change_clothing_talisman");
                    }

                    @Nullable
                    @Override
                    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                        return new ChangeClothingContainer(id, playerInventory, hand);
                    }
                }, buffer -> buffer.writeBoolean(hand == Hand.MAIN_HAND));
            }else if (ChangeClothingTalismanItem.canUse(item)){
                NonNullList<ItemStack> armors = NonNullList.withSize(4, ItemStack.EMPTY);
                ChangeClothingTalismanItem.read(armors, item);
                if (ChangeClothingTalismanItem.isEmpty(armors) && ChangeClothingTalismanItem.isEmpty(player.inventory.armorInventory)) {
                    LOGGER.warn("Empty armor inventory and change_clothing_talisman inventory");
                    player.sendMessage(new TranslationTextComponent("chat.talisman.talisman.cloth.empty").mergeStyle(TextFormatting.YELLOW), Util.DUMMY_UUID);
                    return;
                }
                ChangeClothingTalismanItem.write(player.inventory.armorInventory, item, true);
                for (int i = 0; i < armors.size(); i++) {
                    player.inventory.armorInventory.set(i, armors.get(armors.size() - i - 1));
                }
                if (!player.isCreative() && !player.isSneaking() && item.getDamage() < item.getMaxDamage()) item.attemptDamageItem(1, player.getRNG(), player);
            }
        }
    }

}
