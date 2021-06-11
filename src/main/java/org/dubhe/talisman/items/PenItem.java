package org.dubhe.talisman.items;

import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;

public class PenItem extends Item implements WithDefaultNbt {
    public PenItem(Properties properties) {
        super(properties);
    }

    @Override
    public CompoundNBT defaultNbt(CompoundNBT nbt) {
        nbt.putInt("level", 1);
        return nbt;
    }
}
