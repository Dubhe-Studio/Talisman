package org.dubhe.talisman.talisman;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nullable;

public abstract class AbstractTalisman {

    private final String name;

    protected AbstractTalisman(String name) {
        this.name = name;
    }

    public abstract void execute(Entity entity, Vector3d pos, @Nullable LivingEntity target) throws Exception;

    public String getName() {
        return this.name;
    }

}
