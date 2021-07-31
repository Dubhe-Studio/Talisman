package org.dubhe.talisman.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dubhe.talisman.item.ChangeClothingTalismanItem;
import org.dubhe.talisman.registry.TContainerTypes;
import org.dubhe.talisman.registry.TItems;

public class ChangeClothingContainer extends Container {
    public static final NullPointerException ERROR_TALISMAN_DATA = new NullPointerException("This talisman is not have change clothing tag");
    private static final EquipmentSlotType[] VALID_EQUIPMENT_SLOTS = new EquipmentSlotType[]{EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET};
    private final ItemInventory inventory;
    private final Hand hand;


    public ChangeClothingContainer(int id, PlayerInventory playerInventory, boolean hand) {
        this(id, playerInventory, hand ? Hand.MAIN_HAND : Hand.OFF_HAND);
    }

    @SuppressWarnings("ConstantConditions")
    public ChangeClothingContainer(int id, PlayerInventory playerInventory, Hand hand) throws NullPointerException {
        super(TContainerTypes.CHANGE_CLOTHING.get(), id);
        this.hand = hand;
        ItemStack item = playerInventory.player.getHeldItem(hand);
        this.inventory = new ItemInventory(item);
        this.inventory.openInventory(playerInventory.player);

        if (!item.hasTag() || !item.getTag().contains("Armors")) throw ERROR_TALISMAN_DATA;
        for (int column = 0; column < VALID_EQUIPMENT_SLOTS.length; column++) {
            EquipmentSlotType type = VALID_EQUIPMENT_SLOTS[column];
            this.addSlot(new Slot(this.inventory, column, 53 + column * 18, 20) {
                @Override
                public int getSlotStackLimit() {
                    return 1;
                }

                @Override
                public boolean isItemValid(ItemStack stack) {
                    return stack.canEquip(type, playerInventory.player) && ChangeClothingTalismanItem.canUse(stack);
                }
            });
        }

        for(int row = 0; row < 3; ++row) {
            for(int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(playerInventory, column + row * 9 + 9, 8 + column * 18, row * 18 + 51));
            }
        }

        for(int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(playerInventory, column, 8 + column * 18, 109));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
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
        if (!ChangeClothingTalismanItem.canUse(this.inventory.item) && this.inventory.isEmpty()) {
            this.inventory.item.shrink(1);
            player.addStat(Stats.ITEM_BROKEN.get(TItems.CHANGE_CLOTHING_TALISMAN.get()));
            player.sendBreakAnimation(this.hand);
        }
        super.onContainerClosed(player);
        this.inventory.closeInventory(player);
    }

    @OnlyIn(Dist.CLIENT)
    public int getArmorIndex() {
        int value = 0;
        for (ItemStack armor : this.inventory.armors) {
            value = value << 1;
            if (!armor.isEmpty()) value++;
        }
        return value;

    }


    static class ItemInventory implements IInventory {
        private final ItemStack item;
        private final NonNullList<ItemStack> armors = NonNullList.withSize(4, ItemStack.EMPTY);

        private ItemInventory(ItemStack item) {
            this.item = item;
            this.read();
        }

        private void read() {
            ChangeClothingTalismanItem.read(this.armors, this.item);
        }

        private void write() {
            ChangeClothingTalismanItem.write(this.armors, this.item);
        }

        @Override
        public int getSizeInventory() {
            return 4;
        }

        @Override
        public boolean isEmpty() {
            this.read();
            return ChangeClothingTalismanItem.isEmpty(this.armors);
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
