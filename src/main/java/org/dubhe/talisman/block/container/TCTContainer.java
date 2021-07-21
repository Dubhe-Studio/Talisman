package org.dubhe.talisman.block.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.IIntArray;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dubhe.talisman.block.tileentity.TCTLeftTileEntity;
import org.dubhe.talisman.registry.TBaseValue;
import org.dubhe.talisman.inventory.TalismanResultInventory;
import org.dubhe.talisman.recipe.OutputAndDemand;
import org.dubhe.talisman.registry.TItems;
import org.dubhe.talisman.slot.ResultSlot;
import org.dubhe.talisman.registry.TContainerTypes;
import org.dubhe.talisman.slot.SpecifySlot;


@SuppressWarnings("NullableProblems")
public class TCTContainer extends Container {
    private final TCTLeftTileEntity tileEntity;
    private final TalismanResultInventory craftResult = new TalismanResultInventory();
    private final PlayerEntity player;
    private int needXp = 0;
    private final IIntArray data = new IIntArray() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0:
                    return tileEntity.getExperience();
                case 1:
                    return craftResult.getStackInSlot(0).isEmpty() ? -1 : needXp;
                case 2:
                    ItemStack pen = tileEntity.getStackInSlot(9);
                    ItemStack ink = tileEntity.getStackInSlot(11);
                    if (pen.isEmpty() && ink.isEmpty()) return 1;      // nothing
                    else if (!pen.isEmpty() && ink.isEmpty()) return 2; // only pen
                    else if (pen.isEmpty() && !ink.isEmpty()) return 3;  // only ink
                    else return 0;
                default:
                    return -1;
            }
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0:
                    tileEntity.setExperience(value);
                    break;
                case 1:
                    needXp = value;
                    break;
            }
        }

        @Override
        public int size() {
            return 3;
        }
    };


    public TCTContainer(int id, PlayerInventory playerInventory, TCTLeftTileEntity tileEntity) {
        super(TContainerTypes.TALISMAN_CRAFTING_TABLE.get(), id);
        this.tileEntity = tileEntity;
        this.player = playerInventory.player;
        tileEntity.setContainer(this);
        tileEntity.openInventory(this.player);

        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 3; ++column) {
                this.addSlot(new Slot(tileEntity, row * 3 + column, 46 + column * 18, 18 + row * 18));
            }
        }

        this.addSlot(new ResultSlot(this.player, tileEntity, this.craftResult, 0, 139, 35));

        this.addSlot(new SpecifySlot(tileEntity, 9, 15, 18, Items.FEATHER, TItems.PEN_PRIMARY.get()));
        this.addSlot(new SpecifySlot(tileEntity, 10, 15, 36, Items.EXPERIENCE_BOTTLE));
        this.addSlot(new SpecifySlot(tileEntity, 11, 15, 54, Items.INK_SAC, TItems.INK_PRIMARY.get()));

        // player inventory 3x9
        for(int row = 0; row < 3; ++row) {
            for(int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(playerInventory, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
            }
        }
        // item bar 1x9
        for(int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(playerInventory, column, 8 + column * 18, 142));
        }

        this.trackIntArray(this.data);

        updateResult();
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
                Item item = itemstack1.getItem();
                if (item == Items.EXPERIENCE_BOTTLE)
                    this.mergeItemStack(itemstack1, 11, 12, false);
                else if (TBaseValue.PEN.contains(item))
                    this.mergeItemStack(itemstack1, 10, 11, false);
                else if (TBaseValue.INK.contains(item))
                    this.mergeItemStack(itemstack1, 12, 13, false);
                if (!this.mergeItemStack(itemstack1, 0, 13, false)) {
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
        this.updateResult();
    }

    private void updateResult() {
        if (this.player instanceof ServerPlayerEntity) {
            OutputAndDemand result = tileEntity.getResult();
            this.setResult(result.getItemStack(), result.getExperience());
            ((ServerPlayerEntity) this.player).connection.sendPacket(new SSetSlotPacket(this.windowId, 9, this.craftResult.getStackInSlot(0)));
        }
    }

    private void setResult(ItemStack stack, int xp) {
        this.craftResult.setInventorySlotContents(0, stack);
        this.craftResult.setExperience(xp);
        this.needXp = xp;
    }

    @OnlyIn(Dist.CLIENT)
    public int getExperience() {
        return this.data.get(0);
    }

    @OnlyIn(Dist.CLIENT)
    public int getNeedXp() {
        return this.data.get(1);
    }

    @OnlyIn(Dist.CLIENT)
    public int getLack() {
        return this.data.get(2);
    }

}
