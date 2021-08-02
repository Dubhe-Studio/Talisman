package org.dubhe.talisman.talisman;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class ThunderTalisman extends AbstractTalisman {
    protected ThunderTalisman() {
        super("thunder", false);
    }

    @Override
    public void execute(Entity entity, Vector3d pos, @Nullable Hand hand, @Nullable LivingEntity target) {
        if (!entity.world.isRemote) {
            LightningBoltEntity lighting = EntityType.LIGHTNING_BOLT.create(entity.world);
            lighting.setPositionAndUpdate(pos.x, pos.y, pos.z);
            ((ServerWorld)entity.world).addEntityAndUniquePassengers(lighting);
        }
    }
}
