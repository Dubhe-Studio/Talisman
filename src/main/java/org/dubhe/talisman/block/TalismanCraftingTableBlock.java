package org.dubhe.talisman.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.dubhe.talisman.block.tileentity.TalismanCraftingTableLeftTileEntity;
import org.dubhe.talisman.block.tileentity.TalismanCraftingTableRightTileEntity;

import javax.annotation.Nullable;

@SuppressWarnings("NullableProblems")
public class TalismanCraftingTableBlock extends HorizontalBlock {
    public static final EnumProperty<TalismanCraftingTablePart> PART = EnumProperty.create("part", TalismanCraftingTablePart.class);

    public TalismanCraftingTableBlock() {
        super(Properties.create(Material.WOOD).hardnessAndResistance(2.5F).sound(SoundType.WOOD));
        this.setDefaultState(this.stateContainer.getBaseState().with(PART, TalismanCraftingTablePart.LEFT));
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        if (facing == getDirectionToOther(state.get(PART), getRightDirection(state.get(HORIZONTAL_FACING)))) {
            return facingState.matchesBlock(this) && facingState.get(PART) != state.get(PART) ? state : Blocks.AIR.getDefaultState();
        } else {
            return super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos);
        }
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!worldIn.isRemote && player.isCreative()) {
            TalismanCraftingTablePart part = state.get(PART);
            if (part == TalismanCraftingTablePart.LEFT) {
                BlockPos blockpos = pos.offset(getDirectionToOther(part, getRightDirection(state.get(HORIZONTAL_FACING))));
                BlockState blockstate = worldIn.getBlockState(blockpos);
                if (blockstate.getBlock() == this && blockstate.get(PART) == TalismanCraftingTablePart.RIGHT) {
                    worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 35);
                    worldIn.playEvent(player, 2001, blockpos, Block.getStateId(blockstate));
                }
            }
        }

        super.onBlockHarvested(worldIn, pos, state, player);
    }

    private static Direction getDirectionToOther(TalismanCraftingTablePart part, Direction direction) {
        return part == TalismanCraftingTablePart.LEFT ? direction : direction.getOpposite();
    }

    @Override
    @SuppressWarnings({"deprecation", "ConstantConditions"})
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!world.isRemote && hand == Hand.MAIN_HAND) {
            if (state.get(PART) == TalismanCraftingTablePart.RIGHT) pos = pos.offset(getLeftDirection(state.get(HORIZONTAL_FACING)));
            TalismanCraftingTableLeftTileEntity tileEntity = (TalismanCraftingTableLeftTileEntity) world.getTileEntity(pos);
            NetworkHooks.openGui((ServerPlayerEntity) player, tileEntity, (PacketBuffer packerBuffer) -> packerBuffer.writeBlockPos(tileEntity.getPos()));
        }
        return ActionResultType.SUCCESS;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction direction = context.getPlacementHorizontalFacing();
        BlockPos blockpos = context.getPos().offset(getRightDirection(direction));
        return context.getWorld().getBlockState(blockpos).isReplaceable(context) ? this.getDefaultState().with(HORIZONTAL_FACING, direction) : null;
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, PART);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, placer, stack);
        if (!world.isRemote) {
            BlockPos blockpos = pos.offset(getRightDirection(state.get(HORIZONTAL_FACING)));
            world.setBlockState(blockpos, state.with(PART, TalismanCraftingTablePart.RIGHT), 3);
            world.updateBlock(pos, Blocks.AIR);
            state.updateNeighbours(world, pos, 3);
        }
    }

    public static Direction getRightDirection(Direction direction) {
        switch (direction) {
            case EAST:
                return Direction.SOUTH;
            case SOUTH:
                return Direction.WEST;
            case WEST:
                return Direction.NORTH;
            case NORTH:
            default:
                return Direction.EAST;
        }
    }

    public static Direction getLeftDirection(Direction direction) {
        switch (direction) {
            case EAST:
                return Direction.NORTH;
            case NORTH:
                return Direction.WEST;
            case WEST:
                return Direction.SOUTH;
            case SOUTH:
            default:
                return Direction.EAST;
        }
    }


    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        if (state.get(PART) == TalismanCraftingTablePart.LEFT) return new TalismanCraftingTableLeftTileEntity();
        else return new TalismanCraftingTableRightTileEntity();
    }

    enum TalismanCraftingTablePart implements IStringSerializable {
        LEFT("left"),
        RIGHT("right");

        private final String name;

        TalismanCraftingTablePart(String name) {
            this.name = name;
        }

        @Override
        public String getString() {
            return this.name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }



}
