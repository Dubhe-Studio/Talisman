package org.dubhe.talisman.items;

import net.minecraft.nbt.CompoundNBT;

public interface WithDefaultNbt {
    /**
     * 添加默认NBT
     * @param nbt 要被添加的tag
     * @return 添加之后的tag，与参数相同
     */
    CompoundNBT defaultNbt(CompoundNBT nbt);
}
