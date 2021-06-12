package org.dubhe.talisman.talisman;

import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import org.dubhe.talisman.entities.TalismanEntity;

public class ExplodeTalisman extends AbstractTalisman {
    protected ExplodeTalisman(String name) {
        super(name);
    }

    @Override
    public void execute(TalismanEntity entity, Vector3d pos) {
        entity.world.createExplosion(entity, pos.x, pos.y, pos.z, 4.0F, Explosion.Mode.BREAK);
    }
}
