package org.dubhe.talisman.registry;

import com.google.common.collect.Lists;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.List;

public class TBaseValue {
    public static final int MAX_EXP = 320;
    public static final List<Item> PEN = Lists.newArrayList(Items.FEATHER, TItems.PEN_PRIMARY.get());
    public static final List<Item> INK = Lists.newArrayList(Items.INK_SAC, TItems.INK_PRIMARY.get());
}
