package org.dubhe.talisman.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ChangeClothingContainer extends Container {
    public static final NullPointerException ERROR_TALISMAN_DATA = new NullPointerException("This talisman is not have change clothing tag");
    private final ItemInventory inventory;
    private final IIntArray data = new IIntArray() {
        @Override
        public int get(int index) {
            int value = 0;
            for (ItemStack armor : inventory.armors) {
                value = value << 1;
                if (!armor.isEmpty()) value++;
            }
            return value;
        }

        @Override
        public void set(int index, int value) {
        }

        @Override
        public int size() {
            return 1;
        }
    };

    @SuppressWarnings("ConstantConditions")
    public ChangeClothingContainer(int id, PlayerInventory playerInventory, ItemStack item) throws NullPointerException {
        super(null, id);
        this.inventory = new ItemInventory(item);

        if (!item.hasTag() || !item.getTag().contains("Armors")) throw ERROR_TALISMAN_DATA;

        this.addSlot(new Slot(inventory, 0, 53, 20));
        this.addSlot(new Slot(inventory, 1, 71, 20));
        this.addSlot(new Slot(inventory, 2, 89, 20));
        this.addSlot(new Slot(inventory, 3, 107, 20));

        for(int row = 0; row < 3; ++row) {
            for(int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(playerInventory, column + row * 9 + 9, 8 + column * 18, row * 18 + 51));
            }
        }

        for(int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(playerInventory, column, 8 + column * 18, 109));
        }

        this.trackIntArray(this.data);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return false;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < this.inventory.getSizeInventory()) {
                if (!this.mergeItemStack(itemstack1, this.inventory.getSizeInventory(), this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, this.inventory.getSizeInventory(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) slot.putStack(ItemStack.EMPTY);
            else slot.onSlotChanged();

        }

        return itemstack;
    }

    public void onContainerClosed(PlayerEntity player) {
        super.onContainerClosed(player);
        this.inventory.closeInventory(player);
    }

    @OnlyIn(Dist.CLIENT)
    public int getArmorIndex() {
        return this.data.get(0);
    }


    static class ItemInventory implements IInventory {
        private final ItemStack item;
        private final NonNullList<ItemStack> armors = NonNullList.withSize(4, ItemStack.EMPTY);

        private ItemInventory(ItemStack item) {
            this.item = item;
            this.read();
        }

        @SuppressWarnings("ConstantConditions")
        private void read() {
            ListNBT listnbt = this.item.getTag().getCompound("Armors").getList("Items", 10);

            for(int i = 0; i < listnbt.size(); ++i) {
                CompoundNBT compoundnbt = listnbt.getCompound(i);
                int j = compoundnbt.getByte("Slot") & 255;
                if (j >= 0 && j < this.armors.size()) this.armors.set(j, ItemStack.read(compoundnbt));
            }
        }

        @SuppressWarnings("ConstantConditions")
        private void write() {
            ListNBT listnbt = new ListNBT();
            for(int i = 0; i < this.armors.size(); ++i) {
                ItemStack itemstack = this.armors.get(i);
                if (!itemstack.isEmpty()) {
                    CompoundNBT nbt = new CompoundNBT();
                    nbt.putByte("Slot", (byte)i);
                    itemstack.write(nbt);
                    listnbt.add(nbt);
                }
            }
            this.item.getTag().put("Armors", listnbt);
        }

        @Override
        public int getSizeInventory() {
            return 4;
        }

        @Override
        public boolean isEmpty() {
            this.read();
            for (ItemStack armor : this.armors) {
                if (!armor.isEmpty()) return false;
            }
            return true;
        }

        @Override
        public ItemStack getStackInSlot(int index) {
            this.read();
            return this.armors.get(index);
        }

        @Override
        public ItemStack decrStackSize(int index, int count) {
            this.read();
            ItemStack itemStack = ItemStackHelper.getAndSplit(this.armors, index, count);
            this.write();
            return itemStack;
        }

        @Override
        public ItemStack removeStackFromSlot(int index) {
            this.read();
            ItemStack itemStack = this.armors.set(index, ItemStack.EMPTY);
            this.write();
            return itemStack;
        }

        @Override
        public void setInventorySlotContents(int index, ItemStack stack) {
            this.read();
            this.armors.set(index, stack);
            this.write();
        }

        @Override
        public void markDirty() {
        }

        @Override
        public boolean isUsableByPlayer(PlayerEntity player) {
            return true;
        }

        @Override
        public void clear() {
            this.armors.clear();
        }
    }

}
