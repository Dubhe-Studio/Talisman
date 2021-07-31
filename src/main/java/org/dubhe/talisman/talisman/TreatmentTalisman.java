package org.dubhe.talisman.talisman;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import org.dubhe.talisman.registry.TEffects;

import javax.annotation.Nullable;
import java.util.List;

public class TreatmentTalisman extends AbstractTalisman {
    protected TreatmentTalisman() {
        super("treatment", true);
    }

    @Override
    public void execute(Entity entity, Vector3d pos, @Nullable Hand hand, @Nullable LivingEntity target) {
        if (!entity.world.isRemote) {
            List<LivingEntity> entities = entity.world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(pos.x - 15, pos.y - 15, pos.z - 15, pos.x - 15, pos.y - 15, pos.z - 15));
            for (LivingEntity e : entities) {
                e.heal((float) (e.getMaxHealth() * 0.2));
                e.addPotionEffect(new EffectInstance(TEffects.ANTIDOTE.get(), 100));
            }
        }
    }
}
