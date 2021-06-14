package org.dubhe.talisman.talisman;

import net.minecraft.util.math.vector.Vector3d;
import org.dubhe.talisman.entity.TalismanEntity;

public abstract class AbstractTalisman {

    private final String name;

    protected AbstractTalisman(String name) {
        this.name = name;
    }

    public abstract void execute(TalismanEntity entity, Vector3d pos);

    public String getName() {
        return this.name;
    }

}
