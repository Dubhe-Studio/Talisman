package org.dubhe.talisman.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class DivineStoneOreBlock extends Block {
    public DivineStoneOreBlock() {
        super(Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(5.0F, 6.0F));
    }
}
