package org.dubhe.talisman.item;

import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;

public class InkItem extends Item implements IWithDefaultNbt {
    public InkItem(Properties properties) {
        super(properties);
    }

    @Override
    public CompoundNBT defaultNbt(CompoundNBT nbt) {
        nbt.putInt("level", 1);
        return nbt;
    }
}
