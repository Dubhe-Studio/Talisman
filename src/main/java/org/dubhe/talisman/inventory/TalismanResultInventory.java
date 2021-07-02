package org.dubhe.talisman.inventory;

import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.item.ItemStack;

public class TalismanResultInventory extends CraftResultInventory {

    private int experience = 0;

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getExperience() {
        return this.experience;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        this.experience = 0;
        return super.decrStackSize(index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        this.experience = 0;
        return super.removeStackFromSlot(index);
    }

    @Override
    public void clear() {
        super.clear();
        this.experience = 0;
    }
}
