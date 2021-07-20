package org.dubhe.talisman.block;

import net.minecraft.block.OreBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.MathHelper;
import org.dubhe.talisman.registry.TBlocks;

import java.util.Random;

public class TOreBlock extends OreBlock {
    public TOreBlock() {
        super(Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(5.0F, 6.0F));
    }

    @Override
    protected int getExperience(Random rand) {
        if (this == TBlocks.CINNABAR_ORE.get()) return MathHelper.nextInt(rand, 0, 5);
        else if (this == TBlocks.DIVINE_STONE_ORE.get()) return MathHelper.nextInt(rand, 1, 5);
        else return 0;
    }
}
