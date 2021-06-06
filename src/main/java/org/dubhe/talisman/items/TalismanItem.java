package org.dubhe.talisman.items;

import net.minecraft.item.Item;
import org.dubhe.talisman.ModInitializer;

import javax.annotation.Nullable;

public class TalismanItem extends Item {

    private boolean throwable;
    private int xp;
    private String execute;

    public TalismanItem() {
        this(true, 0, null);

    }

    public TalismanItem(boolean throwable, int xp, @Nullable String execute) {
        super(new Properties().group(ModInitializer.TalismanItemGroup));
    }

}
