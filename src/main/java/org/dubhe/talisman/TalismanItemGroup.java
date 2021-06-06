package org.dubhe.talisman;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.dubhe.talisman.registry.ItemRegistry;

public class TalismanItemGroup extends ItemGroup {

    public TalismanItemGroup() {
        super("talisman_group");
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public ItemStack createIcon() {
        return new ItemStack(ItemRegistry.TALISMAN.get());
    }
}
