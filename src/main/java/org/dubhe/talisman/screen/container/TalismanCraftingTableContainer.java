package org.dubhe.talisman.screen.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dubhe.talisman.registry.ContainerTypeRegistry;


public class TalismanCraftingTableContainer extends Container {
    public TalismanCraftingTableContainer(int id, PlayerInventory inventory, BlockPos pos, World world) {
        super(ContainerTypeRegistry.TALISMAN_CRAFTING_TABLE.get(), id);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }
}
