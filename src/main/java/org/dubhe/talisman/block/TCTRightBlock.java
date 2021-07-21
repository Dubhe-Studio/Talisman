package org.dubhe.talisman.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.dubhe.talisman.block.tileentity.TCTRightTileEntity;
import org.dubhe.talisman.block.tileentity.TCTLeftTileEntity;
import org.dubhe.talisman.registry.TBlocks;
import org.dubhe.talisman.registry.TStats;

import javax.annotation.Nullable;

@SuppressWarnings("NullableProblems")
public class TCTRightBlock extends TCTLeftBlock {

    public TCTRightBlock() {
        super();
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(PEN, Boolean.FALSE).with(INK, Boolean.FALSE));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        switch (state.get(HORIZONTAL_FACING)) {
            default:
            case EAST: return SOUTH_SHAPE;
            case SOUTH: return WEST_SHAPE;
            case WEST: return NORTH_SHAPE;
            case NORTH: return EAST_SHAPE;
        }
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        return facing == getLeftDirection(state.get(HORIZONTAL_FACING)) ?
                facingState.matchesBlock(TBlocks.TALISMAN_CRAFTING_TABLE.get()) ? state : Blocks.AIR.getDefaultState() :
                this.superUpdatePostPlacement(state, facing, facingState, world, currentPos, facingPos);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!world.isRemote && hand == Hand.MAIN_HAND) {
            pos = getOtherPartPos(pos, state.get(HORIZONTAL_FACING), false);
            TCTLeftTileEntity tileEntity = (TCTLeftTileEntity) world.getTileEntity(pos);
            NetworkHooks.openGui((ServerPlayerEntity) player, tileEntity, (PacketBuffer packerBuffer) -> packerBuffer.writeBlockPos(tileEntity.getPos()));
            player.addStat(TStats.INTERACT_WITH_TALISMAN_CRAFTING_TABLE);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.CONSUME;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction direction = context.getPlacementHorizontalFacing();
        return this.getDefaultState().with(HORIZONTAL_FACING, direction);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isRemote) {
            if (player.isCreative()) {
                BlockPos blockpos = getOtherPartPos(pos, state.get(HORIZONTAL_FACING), false);
                BlockState blockstate = world.getBlockState(blockpos);
                if (blockstate.getBlock() == TBlocks.TALISMAN_CRAFTING_TABLE.get()) {
                    world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 35);
                    world.playEvent(player, 2001, blockpos, Block.getStateId(blockstate));
                }
            }
        }
        super.superOnBlockHarvested(world, pos, state, player);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TCTRightTileEntity();
    }

}
