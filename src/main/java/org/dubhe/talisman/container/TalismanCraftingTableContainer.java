package org.dubhe.talisman.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SSetSlotPacket;
import org.dubhe.talisman.block.tileentity.TalismanCraftingTableLeftTileEntity;
import org.dubhe.talisman.registry.ContainerTypeRegistry;
import org.dubhe.talisman.slot.ResultSlot;
import org.dubhe.talisman.slot.SpecifySlot;


@SuppressWarnings("NullableProblems")
public class TalismanCraftingTableContainer extends Container {
    private final TalismanCraftingTableLeftTileEntity tileEntity;
    private final CraftResultInventory craftResult = new CraftResultInventory();
    private final PlayerEntity player;

    public TalismanCraftingTableContainer(int id, PlayerInventory playerInventory, TalismanCraftingTableLeftTileEntity tileEntity) {
        super(ContainerTypeRegistry.TALISMAN_CRAFTING_TABLE.get(), id);
        this.tileEntity = tileEntity;
        this.player = playerInventory.player;
        tileEntity.setContainer(this);
        tileEntity.openInventory(this.player);

        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 3; ++column) {
                this.addSlot(new Slot(tileEntity, row * 3 + column, 46 + column * 18, 18 + row * 18));
            }
        }

        this.addSlot(new ResultSlot(this.player, tileEntity.getCraftingInventory(), this.craftResult, 0, 139, 35));

        this.addSlot(new SpecifySlot(tileEntity, 9, 15, 18));
        this.addSlot(new SpecifySlot(tileEntity, 10, 15, 36));
        this.addSlot(new SpecifySlot(tileEntity, 11, 15, 54));


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
        updateResultUI();
    }

    @Override
    public boolean canInteractWith(PlayerEntity player) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index) {

        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < 13) {
                if (index == 9) itemstack1.getItem().onCreated(itemstack1, player.world, player);
                if (!this.mergeItemStack(itemstack1, 13, 49, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemstack1, itemstack);
            } else if (index < 49) {
                if (!this.mergeItemStack(itemstack1, 0, 12, false)) {
                    if (index < 40) {
                        if (!this.mergeItemStack(itemstack1, 40, 49, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.mergeItemStack(itemstack1, 13, 40, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            ItemStack itemstack2 = slot.onTake(player, itemstack1);
            if (index == 0) {
                player.dropItem(itemstack2, false);
            }
        }

        return itemstack;

    }

    @Override
    public void onContainerClosed(PlayerEntity player) {
        super.onContainerClosed(player);
        this.tileEntity.closeInventory(player);
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventory) {
        this.updateResultUI();
    }

    private void updateResultUI() {
        if (this.player instanceof ServerPlayerEntity) {
            craftResult.setInventorySlotContents(0, tileEntity.getResult());
            ((ServerPlayerEntity) player).connection.sendPacket(new SSetSlotPacket(windowId, 9, craftResult.getStackInSlot(0)));
        }
    }

}
