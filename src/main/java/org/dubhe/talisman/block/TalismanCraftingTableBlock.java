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
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

@SuppressWarnings("NullableProblems")
public class TalismanCraftingTableBlock extends HorizontalBlock {
    public static final EnumProperty<TalismanCraftingTablePart> PART = EnumProperty.create("part", TalismanCraftingTablePart.class);

    public TalismanCraftingTableBlock() {
        super(Properties.create(Material.WOOD).hardnessAndResistance(2.5F).sound(SoundType.WOOD));
        this.setDefaultState(this.stateContainer.getBaseState().with(PART, TalismanCraftingTablePart.LEFT));
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        if (facing == getDirectionToOther(state.get(PART), this.getDirection(state.get(HORIZONTAL_FACING)))) {
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
                BlockPos blockpos = pos.offset(getDirectionToOther(part, this.getDirection(state.get(HORIZONTAL_FACING))));
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

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction direction = context.getPlacementHorizontalFacing();
        BlockPos blockpos = context.getPos().offset(this.getDirection(direction));
        return context.getWorld().getBlockState(blockpos).isReplaceable(context) ? this.getDefaultState().with(HORIZONTAL_FACING, direction) : null;
    }

    @Override
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
            BlockPos blockpos = pos.offset(this.getDirection(state.get(HORIZONTAL_FACING)));
            world.setBlockState(blockpos, state.with(PART, TalismanCraftingTablePart.RIGHT), 3);
            world.updateBlock(pos, Blocks.AIR);
            state.updateNeighbours(world, pos, 3);
        }
    }

    private Direction getDirection(Direction direction) {
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

    @Override
    public boolean hasTileEntity(BlockState state) {
        return false;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return super.createTileEntity(state, world);
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
