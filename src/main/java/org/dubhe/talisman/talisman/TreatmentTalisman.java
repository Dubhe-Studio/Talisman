package org.dubhe.talisman.talisman;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nullable;

public class TreatmentTalisman extends AbstractTalisman {
    protected TreatmentTalisman() {
        super("treatment");
    }

    @Override
    public void execute(Entity entity, Vector3d pos, @Nullable LivingEntity target) {

    }
}
