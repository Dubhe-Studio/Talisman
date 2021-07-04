package org.dubhe.talisman.block.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import org.dubhe.talisman.block.TalismanCraftingTableBlock;
import org.dubhe.talisman.registry.TTileEntityTypes;

@SuppressWarnings({"NullableProblems", "ConstantConditions"})
public class TalismanCraftingTableRightTileEntity extends TileEntity implements ITickableTileEntity {

    public TalismanCraftingTableRightTileEntity() {
        super(TTileEntityTypes.TALISMAN_CRAFTING_TABLE.get());
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        this.getLeftTileEntity().read(this.getLeftBlockState(), this.setPos(nbt, this.getLeftBlockPos()));
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        return this.setPos(this.getLeftTileEntity().write(compound), this.pos);
    }

    private BlockPos getLeftBlockPos() {
        return this.pos.offset(TalismanCraftingTableBlock.getLeftDirection(this.world.getBlockState(this.pos).get(TalismanCraftingTableBlock.HORIZONTAL_FACING)));
    }

    private TileEntity getLeftTileEntity() {
        return this.world.getTileEntity(getLeftBlockPos());
    }

    private BlockState getLeftBlockState() {
        return this.world.getBlockState(getLeftBlockPos());
    }

    private CompoundNBT setPos(CompoundNBT compound, BlockPos pos) {
        compound.putInt("x", pos.getX());
        compound.putInt("y", pos.getY());
        compound.putInt("z", pos.getZ());
        return compound;
    }
    @Override
    public void tick() {

    }
}
