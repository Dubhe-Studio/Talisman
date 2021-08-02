package org.dubhe.talisman.talisman;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import org.dubhe.talisman.entity.TalismanEntity;

import javax.annotation.Nullable;

public class TransferTalisman extends AbstractTalisman {
    protected TransferTalisman() {
        super("transfer", true);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void execute(Entity entity, Vector3d pos, @Nullable Hand hand, @Nullable LivingEntity target) {
        if (entity instanceof TalismanEntity) entity = ((TalismanEntity)entity).getOwner();
        if (target == null) {
            entity.setPositionAndUpdate(pos.x, pos.y, pos.z);
        }else {
            Vector3d before = target.getPositionVec();
            target.setPositionAndUpdate(entity.getPosX(), entity.getPosY(), entity.getPosZ());
            entity.setPositionAndUpdate(before.x, before.y, before.z);
        }
    }
}
