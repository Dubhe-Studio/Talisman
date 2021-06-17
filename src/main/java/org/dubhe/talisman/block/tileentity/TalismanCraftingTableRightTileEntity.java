package org.dubhe.talisman.block.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import org.dubhe.talisman.registry.TileEntityTypeRegistry;

@SuppressWarnings("NullableProblems")
public class TalismanCraftingTableRightTileEntity extends TileEntity implements ITickableTileEntity {

    private final TalismanCraftingTableLeftTileEntity leftTileEntity;

    public TalismanCraftingTableRightTileEntity(TalismanCraftingTableLeftTileEntity leftTileEntity) {
        super(TileEntityTypeRegistry.TALISMAN_CRAFTING_TABLE.get());
        this.leftTileEntity = leftTileEntity;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        leftTileEntity.read(state, nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        return leftTileEntity.write(compound);
    }

    @Override
    public void tick() {

    }
}
