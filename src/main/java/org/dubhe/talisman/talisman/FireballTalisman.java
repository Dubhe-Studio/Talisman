package org.dubhe.talisman.talisman;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import org.dubhe.talisman.registry.TEffects;

import javax.annotation.Nullable;

public class FireballTalisman extends AbstractTalisman {
    protected FireballTalisman() {
        super("fireball", false);
    }

    @Override
    public void execute(Entity entity, Vector3d pos, @Nullable Hand hand, @Nullable LivingEntity target) {
        if (target != null) {
            target.addPotionEffect(new EffectInstance(TEffects.BURNING.get(), 300));
        }
    }
}
