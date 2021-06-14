package org.dubhe.talisman.item;

import net.minecraft.nbt.CompoundNBT;

public interface WithDefaultNbt {
    /**
     * default NBT
     * @param nbt added tag
     * @return result
     */
    CompoundNBT defaultNbt(CompoundNBT nbt);
}
