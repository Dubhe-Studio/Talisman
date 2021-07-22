package org.dubhe.talisman.block.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import org.dubhe.talisman.block.TCTLeftBlock;
import org.dubhe.talisman.registry.TBlocks;
import org.dubhe.talisman.registry.TTileEntityTypes;

@SuppressWarnings({"NullableProblems", "ConstantConditions"})
public class TCTRightTileEntity extends TileEntity implements ITickableTileEntity {

    public TCTRightTileEntity() {
        super(TTileEntityTypes.TALISMAN_CRAFTING_TABLE_RIGHT.get());
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        this.getLeftTileEntity().read(this.getLeftBlockState(), this.setNBT(nbt, this.getLeftBlockPos()));
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        return this.setNBT(this.getLeftTileEntity().write(compound), this.pos);
    }

    private BlockPos getLeftBlockPos() {
        return this.pos.offset(TCTLeftBlock.getLeftDirection(this.world.getBlockState(this.pos).get(TCTLeftBlock.HORIZONTAL_FACING)));
    }

    private TileEntity getLeftTileEntity() {
        return this.world.getTileEntity(getLeftBlockPos());
    }

    private BlockState getLeftBlockState() {
        return this.world.getBlockState(getLeftBlockPos());
    }

    private CompoundNBT setNBT(CompoundNBT compound, BlockPos pos) {
        compound.putInt("x", pos.getX());
        compound.putInt("z", pos.getZ());
        compound.putString("id", TBlocks.TALISMAN_CRAFTING_TABLE_RIGHT.get().getRegistryName().toString());
        return compound;
    }
    @Override
    public void tick() {
        if (!this.world.isRemote) {
            BlockState right = this.world.getBlockState(this.pos);
            BlockState left = getLeftBlockState();
            boolean leftPen = left.get(TCTLeftBlock.PEN);
            boolean leftInk = left.get(TCTLeftBlock.INK);
            boolean rightPen = right.get(TCTLeftBlock.PEN);
            boolean rightInk = right.get(TCTLeftBlock.INK);
            if (leftPen != rightPen || leftInk != rightInk) {
                this.world.setBlockState(this.pos, right.with(TCTLeftBlock.PEN , leftPen).with(TCTLeftBlock.INK, leftInk));
                this.markDirty();
            }
        }

    }
}
