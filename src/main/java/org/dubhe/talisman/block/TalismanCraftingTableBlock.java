package org.dubhe.talisman.block;

import net.minecraft.block.Block;
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
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
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
    public static final BooleanProperty PEN = BooleanProperty.create("pen");
    public static final BooleanProperty INK = BooleanProperty.create("ink");
    private final static VoxelShape EAST_SHAPE;
    private final static VoxelShape WEST_SHAPE;
    private final static VoxelShape SOUTH_SHAPE;
    private final static VoxelShape NORTH_SHAPE;

    public TalismanCraftingTableBlock() {
        super(Properties.create(Material.WOOD).hardnessAndResistance(2.5F).sound(SoundType.WOOD).notSolid());
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(PART, TalismanCraftingTablePart.LEFT).with(PEN, Boolean.FALSE).with(INK, Boolean.FALSE));
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        Direction direction = state.get(HORIZONTAL_FACING);
        boolean isLeft = state.get(PART) == TalismanCraftingTablePart.LEFT;
        switch (direction) {
            case EAST:
            default:
                return isLeft ? NORTH_SHAPE : SOUTH_SHAPE;
            case SOUTH:
                return isLeft ? EAST_SHAPE : WEST_SHAPE;
            case WEST:
                return isLeft ? SOUTH_SHAPE : NORTH_SHAPE;
            case NORTH:
                return isLeft ? WEST_SHAPE : EAST_SHAPE;
        }
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
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, PART, PEN, INK);
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

    static {
        VoxelShape leg1 = Block.makeCuboidShape(13, 0, 1, 14, 10, 2);
        VoxelShape leg2 = Block.makeCuboidShape(13, 0, 14, 14, 10, 15);
        VoxelShape point1 = Block.makeCuboidShape(12, 8, 1, 13, 9, 2);
        VoxelShape point2 = Block.makeCuboidShape(13, 8, 2, 14, 9, 3);
        VoxelShape point3 = Block.makeCuboidShape(12, 8, 14, 13, 9, 15);
        VoxelShape point4 = Block.makeCuboidShape(13, 8, 13, 14, 9, 14);
        VoxelShape foothold1 = Block.makeCuboidShape(11, 8, 1, 12, 10, 2);
        VoxelShape foothold2 = Block.makeCuboidShape(13, 8, 3, 14, 10, 4);
        VoxelShape foothold3 = Block.makeCuboidShape(11, 8, 14, 12, 10, 15);
        VoxelShape foothold4 = Block.makeCuboidShape(13, 8, 12, 14, 10, 13);
        VoxelShape edge = Block.makeCuboidShape(15, 11, 0, 16, 12, 16);
        VoxelShape face = Block.makeCuboidShape(0, 10, 0, 15, 11, 16);
        EAST_SHAPE = VoxelShapes.or(leg1, leg2, point1, point2, point3, point4, foothold1, foothold2, foothold3, foothold4, edge, face);
        leg1 = Block.makeCuboidShape(2, 0, 1, 3, 10, 2);
        leg2 = Block.makeCuboidShape(2, 0, 14, 3, 10, 15);
        point1 = Block.makeCuboidShape(3, 8, 1, 4, 9, 2);
        point2 = Block.makeCuboidShape(2, 8, 2, 3, 9, 3);
        point3 = Block.makeCuboidShape(3, 8, 14, 4, 9, 15);
        point4 = Block.makeCuboidShape(2, 8, 13, 3, 9, 14);
        foothold1 = Block.makeCuboidShape(4, 8, 1, 5, 10, 2);
        foothold2 = Block.makeCuboidShape(2, 8, 3, 3, 10, 4);
        foothold3 = Block.makeCuboidShape(4, 8, 14, 5, 10, 15);
        foothold4 = Block.makeCuboidShape(2, 8, 12, 3, 10, 13);
        edge = Block.makeCuboidShape(0, 11, 0, 1, 12, 16);
        face = Block.makeCuboidShape(1, 10, 0, 16, 11, 16);
        WEST_SHAPE = VoxelShapes.or(leg1, leg2, point1, point2, point3, point4, foothold1, foothold2, foothold3, foothold4, edge, face);
        leg1 = Block.makeCuboidShape(1, 0, 13, 2, 10, 14);
        leg2 = Block.makeCuboidShape(14, 0, 13, 15, 10, 14);
        point1 = Block.makeCuboidShape(1, 8, 12, 2, 9, 13);
        point2 = Block.makeCuboidShape(2, 8, 13, 3, 9, 14);
        point3 = Block.makeCuboidShape(14, 8, 12, 15, 9, 13);
        point4 = Block.makeCuboidShape(13, 8, 13, 14, 9, 14);
        foothold1 = Block.makeCuboidShape(1, 8, 11, 2, 10, 12);
        foothold2 = Block.makeCuboidShape(3, 8, 13, 4, 10, 14);
        foothold3 = Block.makeCuboidShape(14, 8, 11, 15, 10, 12);
        foothold4 = Block.makeCuboidShape(12, 8, 13, 13, 10, 14);
        edge = Block.makeCuboidShape(0, 11, 15, 16, 12, 16);
        face = Block.makeCuboidShape(0, 10, 0, 16, 11, 15);
        SOUTH_SHAPE = VoxelShapes.or(leg1, leg2, point1, point2, point3, point4, foothold1, foothold2, foothold3, foothold4, edge, face);
        leg1 = Block.makeCuboidShape(1, 0, 2, 2, 10, 3);
        leg2 = Block.makeCuboidShape(14, 0, 2, 15, 10, 3);
        point1 = Block.makeCuboidShape(1, 8, 3, 2, 9, 4);
        point2 = Block.makeCuboidShape(2, 8, 2, 3, 9, 3);
        point3 = Block.makeCuboidShape(14, 8, 3, 15, 9, 4);
        point4 = Block.makeCuboidShape(13, 8, 2, 14, 9, 3);
        foothold1 = Block.makeCuboidShape(1, 8, 4, 2, 10, 5);
        foothold2 = Block.makeCuboidShape(3, 8, 2, 4, 10, 3);
        foothold3 = Block.makeCuboidShape(14, 8, 4, 15, 10, 5);
        foothold4 = Block.makeCuboidShape(12, 8, 2, 13, 10, 3);
        edge = Block.makeCuboidShape(0, 11, 0, 16, 12, 1);
        face = Block.makeCuboidShape(0, 10, 1, 16, 11, 16);
        NORTH_SHAPE = VoxelShapes.or(leg1, leg2, point1, point2, point3, point4, foothold1, foothold2, foothold3, foothold4, edge, face);
    }

    enum TalismanCraftingTablePart implements IStringSerializable {
        LEFT,
        RIGHT;

        @Override
        public String toString() {
            return this.getString();
        }

        @Override
        public String getString() {
            return this == LEFT ? "left" : "right";
        }

    }

}
