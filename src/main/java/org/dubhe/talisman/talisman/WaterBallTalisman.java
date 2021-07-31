package org.dubhe.talisman.talisman;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nullable;

public class WaterBallTalisman extends AbstractTalisman {
    protected WaterBallTalisman() {
        super("water_ball", true);
    }

    @Override
    public void execute(Entity entity, Vector3d pos, @Nullable Hand hand, @Nullable LivingEntity target) {

    }
}
