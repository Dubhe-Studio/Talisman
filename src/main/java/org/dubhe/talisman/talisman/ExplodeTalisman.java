package org.dubhe.talisman.talisman;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;

public class ExplodeTalisman extends AbstractTalisman {
    protected ExplodeTalisman(String name) {
        super(name);
    }

    @Override
    public void execute(Entity entity, Vector3d pos) {
        entity.world.createExplosion(entity, pos.x, pos.y, pos.z, 4.0F, Explosion.Mode.BREAK);
    }
}
