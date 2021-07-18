package org.dubhe.talisman.registry;

import net.minecraft.stats.IStatFormatter;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;

public class TStats {
    public static final ResourceLocation INTERACT_WITH_TALISMAN_CRAFTING_TABLE = Stats.registerCustom("interact_with_talisman_crafting_table", IStatFormatter.DEFAULT);

    public static void register() {}

}
