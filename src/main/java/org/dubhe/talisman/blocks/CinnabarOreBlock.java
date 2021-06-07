package org.dubhe.talisman.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class CinnabarOreBlock extends Block {
    public CinnabarOreBlock() {
        super(Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(5.0F, 6.0F));
    }
}
