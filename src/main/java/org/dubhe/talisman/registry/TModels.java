package org.dubhe.talisman.registry;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dubhe.talisman.ModInitializer;

@OnlyIn(Dist.CLIENT)
public enum TModels {
    EXPLODE("talisman/explode"),
    TRANSFER("talisman/transfer"),
    FIREBALL("talisman/fireball"),
    IMMOBILITY("talisman/immobility"),
    TREATMENT("talisman/treatment"),
    PUPPET("talisman/puppet"),
    SEPARATION("talisman/separation"),
    THUNDER("talisman/thunder"),
    MUTE("talisman/mute"),
    CARRY("talisman/carry"),
    WATER_BALL("talisman/water_ball"),
    DOOM("talisman/doom"),
    HUGE_EXPLOSION("talisman/huge_explosion"),
    CHANGE_CLOTHING("talisman/change_clothing");

    private final String namespace;

    TModels(String namespace) {
        this.namespace = namespace;
    }

    public String getNamespace() {
        return ModInitializer.getIdentifier(this.namespace).toString();
    }
}
