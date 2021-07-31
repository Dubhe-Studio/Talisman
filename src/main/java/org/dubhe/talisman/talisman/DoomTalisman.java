package org.dubhe.talisman.talisman;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;

import javax.annotation.Nullable;

public class DoomTalisman extends AbstractTalisman {
    protected DoomTalisman() {
        super("doom", false);
    }

    @Override
    public void execute(Entity entity, Vector3d pos, @Nullable Hand hand, @Nullable LivingEntity target) {
        entity.world.createExplosion(entity, pos.x, pos.y, pos.z, 16.0F, Explosion.Mode.BREAK);
    }
}
