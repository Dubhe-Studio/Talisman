package org.dubhe.talisman.talisman;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nullable;

public abstract class AbstractTalisman {

    private final String name;
    private final boolean beneficial;

    protected AbstractTalisman(String name, boolean beneficial) {
        this.name = name;
        this.beneficial = beneficial;
    }

    public boolean isBeneficial() {
        return beneficial;
    }

    public abstract void execute(Entity entity, Vector3d pos, @Nullable LivingEntity target) throws Exception;

    public String getName() {
        return this.name;
    }

}
