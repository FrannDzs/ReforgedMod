package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.base.WaterloggedDirectionalShape;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

@Assets(
        state = @State(name = "%s_small_window_half", template = "parent_window_small_half"),
        item = @Model(name = "item/%s_small_window_half", parent = "block/%s_window_small_half_updown", template = "item/parent_window_small_half"),
        block = {
                @Model(name = "block/%s_window_small_half", template = "block/parent_window_small_half"),
                @Model(name = "block/%s_window_small_half_down", template = "block/parent_window_small_half_down"),
                @Model(name = "block/%s_window_small_half_up", template = "block/parent_window_small_half_up"),
                @Model(name = "block/%s_window_small_half_updown", template = "block/parent_window_small_half_updown"),
        }
)
public class WindowSmallHalf extends WaterloggedDirectionalShape {

    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;

    private static final VoxelShape TOPLEFT = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 4.0D, 16.0D, 4.0D);
    private static final VoxelShape TOPRIGHT = Block.makeCuboidShape(12.0D, 0.0D, 0.0D, 16.0D, 16.0D, 4.0D);
    private static final VoxelShape BOTTOMLEFT = Block.makeCuboidShape(0.0D, 0.0D, 12.0D, 4.0D, 16.0D, 16.0D);
    private static final VoxelShape BOTTOMRIGHT = Block.makeCuboidShape(12.0D, 0.0D, 12.0D, 16.0D, 16.0D, 16.0D);

    private static final VoxelShape SOUTH_SHAPE = VoxelShapes.or(TOPLEFT, TOPRIGHT);
    private static final VoxelShape WEST_SHAPE = VoxelShapes.or(BOTTOMRIGHT, TOPRIGHT);
    private static final VoxelShape NORTH_SHAPE = VoxelShapes.or(BOTTOMLEFT, BOTTOMRIGHT);
    private static final VoxelShape EAST_SHAPE = VoxelShapes.or(BOTTOMLEFT, TOPLEFT);

    private static final VoxelShape TOP_NORTH = Block.makeCuboidShape(4.0D, 13.5D, 12.0D, 12.0D, 16.0D, 16.0D);
    private static final VoxelShape TOP_EAST = Block.makeCuboidShape(0.0D, 13.5D, 4.0D, 4.0D, 16.0D, 12.0D);
    private static final VoxelShape TOP_SOUTH = Block.makeCuboidShape(4.0D, 13.5D, 0.0D, 12.0D, 16.0D, 4.0D);
    private static final VoxelShape TOP_WEST = Block.makeCuboidShape(12.0D, 13.5D, 4.0D, 16.0D, 16.0D, 12.0D);

    private static final VoxelShape BOTTOM_NORTH = Block.makeCuboidShape(4.0D, 0.0D, 12.0D, 12.0D, 2.5D, 16.0D);
    private static final VoxelShape BOTTOM_EAST = Block.makeCuboidShape(0.0D, 0.0D, 4.0D, 4.0D, 2.5D, 12.0D);
    private static final VoxelShape BOTTOM_SOUTH = Block.makeCuboidShape(4.0D, 0.0D, 0.0D, 12.0D, 2.5D, 4.0D);
    private static final VoxelShape BOTTOM_WEST = Block.makeCuboidShape(12.0D, 0.0D, 4.0D, 16.0D, 2.5D, 12.0D);

    private static final VoxelShape UP_SOUTH_SHAPE = VoxelShapes.or(SOUTH_SHAPE, TOP_SOUTH);
    private static final VoxelShape UP_WEST_SHAPE = VoxelShapes.or(WEST_SHAPE, TOP_WEST);
    private static final VoxelShape UP_NORTH_SHAPE = VoxelShapes.or(NORTH_SHAPE, TOP_NORTH);
    private static final VoxelShape UP_EAST_SHAPE = VoxelShapes.or(EAST_SHAPE, TOP_EAST);

    private static final VoxelShape DOWN_SOUTH_SHAPE = VoxelShapes.or(SOUTH_SHAPE, BOTTOM_SOUTH);
    private static final VoxelShape DOWN_WEST_SHAPE = VoxelShapes.or(WEST_SHAPE, BOTTOM_WEST);
    private static final VoxelShape DOWN_NORTH_SHAPE = VoxelShapes.or(NORTH_SHAPE, BOTTOM_NORTH);
    private static final VoxelShape DOWN_EAST_SHAPE = VoxelShapes.or(EAST_SHAPE, BOTTOM_EAST);

    private static final VoxelShape UPDOWN_SOUTH_SHAPE = VoxelShapes.or(SOUTH_SHAPE, VoxelShapes.or(TOP_SOUTH, BOTTOM_SOUTH));
    private static final VoxelShape UPDOWN_WEST_SHAPE = VoxelShapes.or(WEST_SHAPE, VoxelShapes.or(TOP_WEST, BOTTOM_WEST));
    private static final VoxelShape UPDOWN_NORTH_SHAPE = VoxelShapes.or(NORTH_SHAPE, VoxelShapes.or(TOP_NORTH, BOTTOM_NORTH));
    private static final VoxelShape UPDOWN_EAST_SHAPE = VoxelShapes.or(EAST_SHAPE, VoxelShapes.or(TOP_EAST, BOTTOM_EAST));

    public WindowSmallHalf(Properties properties) {
        super(properties);
        setDefaultState((stateContainer.getBaseState()).with(DIRECTION, Direction.NORTH).with(UP,false).with(DOWN,false).with(WATERLOGGED, false));
    }

    public VoxelShape getShape(BlockState state) {
        if (state.get(DOWN) && state.get(UP)) {
            switch (state.get(DIRECTION)) {
                case NORTH:
                default:
                    return NORTH_SHAPE;
                case SOUTH:
                    return SOUTH_SHAPE;
                case WEST:
                    return WEST_SHAPE;
                case EAST:
                    return EAST_SHAPE;
            }
        } else if (!state.get(DOWN) && state.get(UP)) {
            switch (state.get(DIRECTION)) {
                case NORTH:
                default:
                    return DOWN_NORTH_SHAPE;
                case SOUTH:
                    return DOWN_SOUTH_SHAPE;
                case WEST:
                    return DOWN_WEST_SHAPE;
                case EAST:
                    return DOWN_EAST_SHAPE;
            }
        } else if (state.get(DOWN) && !state.get(UP)) {
            switch (state.get(DIRECTION)) {
                case NORTH:
                default:
                    return UP_NORTH_SHAPE;
                case SOUTH:
                    return UP_SOUTH_SHAPE;
                case WEST:
                    return UP_WEST_SHAPE;
                case EAST:
                    return UP_EAST_SHAPE;
            }
        } else {
            switch (state.get(DIRECTION)) {
                case NORTH:
                default:
                    return UPDOWN_NORTH_SHAPE;
                case SOUTH:
                    return UPDOWN_SOUTH_SHAPE;
                case WEST:
                    return UPDOWN_WEST_SHAPE;
                case EAST:
                    return UPDOWN_EAST_SHAPE;
            }
        }

    }

    @Override
    protected void addProperties(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IBlockReader iblockreader = context.getWorld();
        BlockPos blockpos = context.getPos();
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        BlockPos up = blockpos.up();
        BlockPos down = blockpos.down();

        BlockState BlockStateUp = iblockreader.getBlockState(up);
        BlockState BlockStateDown = iblockreader.getBlockState(down);

        Direction facing = context.getPlacementHorizontalFacing().getOpposite();
        return super.getStateForPlacement(context)
                .with(DIRECTION, facing)
                .with(UP, this.attachesTo(BlockStateUp))
                .with(DOWN, this.attachesTo(BlockStateDown))
                .with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        boolean flag = this.canConnectTo(worldIn, currentPos.up());
        boolean flag1 = this.canConnectTo(worldIn, currentPos.down());
        return stateIn.with(UP, flag).with(DOWN, flag1);
    }

    private boolean attachesTo(BlockState blockstate) {
        Block block = blockstate.getBlock();
        return block != Blocks.BARRIER && (!(block != this && !(block instanceof WindowSmallHalf)));
    }

    private boolean canConnectTo(IWorld worldIn, BlockPos pos) {
        BlockState BlockState = worldIn.getBlockState(pos);
        Block block = BlockState.getBlock();
        return block != Blocks.BARRIER && (!(block != this && !(block instanceof WindowSmallHalf)));
    }
}
