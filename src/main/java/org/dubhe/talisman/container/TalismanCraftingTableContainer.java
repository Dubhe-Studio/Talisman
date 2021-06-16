package org.dubhe.talisman.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.dubhe.talisman.block.tileentity.TalismanCraftingTableTileEntity;
import org.dubhe.talisman.registry.ContainerTypeRegistry;
import org.dubhe.talisman.slot.ResultSlot;
import org.dubhe.talisman.slot.SpecifySlot;


@SuppressWarnings("NullableProblems")
public class TalismanCraftingTableContainer extends Container {
    private final TalismanCraftingTableTileEntity tileEntity;

    public TalismanCraftingTableContainer(int id, PlayerInventory playerInventory, TalismanCraftingTableTileEntity tileEntity) {
        super(ContainerTypeRegistry.TALISMAN_CRAFTING_TABLE.get(), id);
        this.tileEntity = tileEntity;
        tileEntity.openInventory(playerInventory.player);

        this.addSlot(new SpecifySlot(tileEntity, 10, 15, 18));
        this.addSlot(new SpecifySlot(tileEntity, 11, 15, 36));
        this.addSlot(new SpecifySlot(tileEntity, 12, 15, 54));

        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 3; ++column) {
                this.addSlot(new Slot(tileEntity, row * 3 + column, 46 + column * 18, 18 + row * 18));
            }
        }

        this.addSlot(new ResultSlot(tileEntity, 9, 139, 35));

        // player inventory 3x9
        for(int row = 0; row < 3; ++row) {
            for(int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(playerInventory, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
            }
        }
        // player inventory 1x9
        for(int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(playerInventory, column, 8 + column * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity player) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack = slot.getStack();
            if (index < 13) {
                this.mergeItemStack(itemStack, 13, 49, true);
            } else {
                this.mergeItemStack(itemStack, 0, 12, false);
            }
        }
        return ItemStack.EMPTY;

    }
}
