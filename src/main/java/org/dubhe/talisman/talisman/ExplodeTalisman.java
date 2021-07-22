package org.dubhe.talisman.talisman;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;

import javax.annotation.Nullable;

public class ExplodeTalisman extends AbstractTalisman {
    protected ExplodeTalisman() {
        super("explode");
    }

    @Override
    public void execute(Entity entity, Vector3d pos, @Nullable LivingEntity target) {
        entity.world.createExplosion(entity, pos.x, pos.y, pos.z, 4.0F, Explosion.Mode.BREAK);
    }
}
