package org.dubhe.talisman.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

/**
 * only input {@link SpecifySlot#canInputs} items
 */
@SuppressWarnings("NullableProblems")
public class SpecifySlot extends Slot {
    private final Class<?>[] canInputs;

    public SpecifySlot(IInventory inventory, int index, int xPosition, int yPosition, Class<?>... canInputs) {
        super(inventory, index, xPosition, yPosition);
        this.canInputs = canInputs;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        for (Class<?> clazz : this.canInputs) {
            if (clazz.isAssignableFrom(stack.getItem().getClass())) return true;
        }
        return false;
    }
}
