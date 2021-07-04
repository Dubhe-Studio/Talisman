package org.dubhe.talisman.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;

public class TalismanCraftingInventory extends CraftingInventory {
    private Container container = new Container(null, -1) {
        @Override
        public boolean canInteractWith(PlayerEntity playerIn) {
            return true;
        }
    };

    public TalismanCraftingInventory() {
        super(new Container(null, -1) {
            @Override
            public boolean canInteractWith(PlayerEntity playerIn) {
                return true;
            }
        }, 3, 3);
    }

    public void setHandler(Container container) {
        this.container = container;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack itemstack = super.decrStackSize(index, count);
        if (!itemstack.isEmpty()) {
            this.container.onCraftMatrixChanged(this);
        }
        return itemstack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        super.setInventorySlotContents(index, stack);
        this.container.onCraftMatrixChanged(this);
    }

    public void onCraftMatrixChanged() {
        this.container.onCraftMatrixChanged(this);
    }

}
