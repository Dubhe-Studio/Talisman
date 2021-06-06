package org.dubhe.talisman.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import org.dubhe.talisman.ModInitializer;
import org.dubhe.talisman.entities.TalismanEntity;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;

public class TalismanItem extends Item {

    private boolean throwable;
    private int xp;
    private final Collection<String> execute;

    public TalismanItem() {
        this(true, 0, null);
    }

    public TalismanItem(boolean throwable, int xp, @Nullable Collection<String> execute) {
        super(new Properties().group(ModInitializer.TalismanItemGroup));
        this.throwable = throwable;
        this.xp = xp;
        this.execute = execute == null ? Collections.emptyList() : execute;
    }

    public TalismanEntity createEntity(World world, PlayerEntity player) {

        return new TalismanEntity(world, player.getPositionVec(), player, this.execute);
    }

}
