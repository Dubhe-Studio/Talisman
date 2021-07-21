package org.dubhe.talisman.slot;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import org.dubhe.talisman.block.tileentity.TCTLeftTileEntity;
import org.dubhe.talisman.inventory.TalismanCraftingInventory;
import org.dubhe.talisman.inventory.TalismanResultInventory;
import org.dubhe.talisman.registry.TRecipes;

import java.util.Random;

/**
 * can't input items
 */
@SuppressWarnings("NullableProblems")
public class ResultSlot extends Slot {
    private final TCTLeftTileEntity tileEntity;
    private final PlayerEntity player;
    private final TalismanCraftingInventory craftMatrix;
    private int amountCrafted;

    public ResultSlot(PlayerEntity player, TCTLeftTileEntity tileEntity, IInventory inventory, int index, int xPosition, int yPosition) {
        super(inventory, index, xPosition, yPosition);
        this.player = player;
        this.craftMatrix = tileEntity.getCraftingInventory();
        this.tileEntity = tileEntity;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canTakeStack(PlayerEntity playerIn) {
        return !this.inventory.isEmpty() && this.tileEntity.canTakeResultStack(((TalismanResultInventory) this.inventory).getExperience());
    }

    @Override
    public ItemStack decrStackSize(int amount) {
        if (this.getHasStack()) {
            this.amountCrafted += Math.min(amount, this.getStack().getCount());
        }

        return super.decrStackSize(amount);
    }

    @Override
    protected void onCrafting(ItemStack stack, int amount) {
        this.amountCrafted += amount;
        this.onCrafting(stack);
    }

    @Override
    protected void onSwapCraft(int numItemsCrafted) {
        this.amountCrafted += numItemsCrafted;
    }

    @Override
    protected void onCrafting(ItemStack stack) {
        if (this.amountCrafted > 0) {
            stack.onCrafting(this.player.world, this.player, this.amountCrafted);
        }

        if (this.inventory instanceof IRecipeHolder) {
            ((IRecipeHolder)this.inventory).onCrafting(this.player);
        }

        this.amountCrafted = 0;
    }

    @Override
    public ItemStack onTake(PlayerEntity player, ItemStack stack) {
        if (!player.world.isRemote) {
            this.tileEntity.shrinkExperience(((TalismanResultInventory)this.inventory).getExperience());
        }
        decrStackSize(this.tileEntity, this.player.getRNG(), 9);
        decrStackSize(this.tileEntity, this.player.getRNG(), 11);

        this.onCrafting(stack);
        net.minecraftforge.common.ForgeHooks.setCraftingPlayer(player);
        NonNullList<ItemStack> list = player.world.getRecipeManager().getRecipeNonNull(TRecipes.TALISMAN_CRAFTING_TYPE, this.craftMatrix, player.world);
        net.minecraftforge.common.ForgeHooks.setCraftingPlayer(null);
        for(int i = 0; i < list.size(); ++i) {
            ItemStack itemStack = this.craftMatrix.getStackInSlot(i);
            ItemStack itemStack1 = list.get(i);
            if (!itemStack.isEmpty()) {
                this.craftMatrix.decrStackSize(i, 1);
                itemStack = this.craftMatrix.getStackInSlot(i);
            }

            if (!itemStack1.isEmpty()) {
                if (itemStack.isEmpty()) {
                    this.craftMatrix.setInventorySlotContents(i, itemStack1);
                } else if (ItemStack.areItemsEqual(itemStack, itemStack1) && ItemStack.areItemStackTagsEqual(itemStack, itemStack1)) {
                    itemStack1.grow(itemStack.getCount());
                    this.craftMatrix.setInventorySlotContents(i, itemStack1);
                } else if (!this.player.inventory.addItemStackToInventory(itemStack1)) {
                    this.player.dropItem(itemStack1, false);
                }
            }
        }

        return stack;
    }

    private static void decrStackSize(TCTLeftTileEntity tileEntity, Random rand, int index) {
        ItemStack stack = tileEntity.getStackInSlot(index);
        if (stack.isDamageable()) {
            if (stack.attemptDamageItem(1, rand, null)) stack.shrink(1);
        } else tileEntity.decrStackSize(index, 1);
    }

}
