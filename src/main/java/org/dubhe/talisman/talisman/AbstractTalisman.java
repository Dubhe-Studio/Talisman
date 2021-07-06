package org.dubhe.talisman.talisman;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.vector.Vector3d;

public abstract class AbstractTalisman {

    private final String name;

    protected AbstractTalisman(String name) {
        this.name = name;
    }

    public abstract void execute(Entity entity, Vector3d pos);

    public String getName() {
        return this.name;
    }

}
