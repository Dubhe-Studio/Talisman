package org.dubhe.talisman.recipe;

import net.minecraft.item.ItemStack;

public class OutputAndDemand {
    public static final OutputAndDemand EMPTY = new OutputAndDemand(ItemStack.EMPTY, 0);
    private final ItemStack itemStack;
    private final int experience;

    protected OutputAndDemand(ItemStack itemStack, int experience) {
        this.itemStack = itemStack;
        this.experience = experience;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getExperience() {
        return experience;
    }

    public OutputAndDemand copy() {
        return new OutputAndDemand(this.itemStack.copy(), this.experience);
    }

    @Override
    public String toString() {
        return "OutputAndDemand: " + this.itemStack + " and " + this.experience + " experiences";
    }
}
