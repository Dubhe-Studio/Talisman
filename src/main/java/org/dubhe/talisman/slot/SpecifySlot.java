package org.dubhe.talisman.slot;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

/**
 * only input {@link SpecifySlot#canInputs} items
 */
@SuppressWarnings("NullableProblems")
public class SpecifySlot extends Slot {
    private final Item[] canInputs;

    public SpecifySlot(IInventory inventory, int index, int xPosition, int yPosition, Item... canInputs) {
        super(inventory, index, xPosition, yPosition);
        this.canInputs = canInputs;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return Arrays.stream(this.canInputs).anyMatch( item -> item == stack.getItem());
    }
}
