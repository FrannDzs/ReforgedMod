package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.properties.Waterloggable;
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
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

@Assets(
        state = @State(name = "%s_small_window", template = "parent_window_small"),
        item = @Model(name = "item/%s_small_window", parent = "block/%s_window_small", template = "item/parent_window_small"),
        block = {
                @Model(name = "block/%s_window_small", template = "block/parent_window_small"),
                @Model(name = "block/%s_window_small_down", template = "block/parent_window_small_down"),
                @Model(name = "block/%s_window_small_up", template = "block/parent_window_small_up"),
                @Model(name = "block/%s_window_small_updown", template = "block/parent_window_small_updown"),
        }
)
public class WindowSmall extends Block implements Waterloggable {

    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;

    private static final VoxelShape TOPLEFT = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 4.0D, 16.0D, 4.0D);
    private static final VoxelShape TOPRIGHT = Block.makeCuboidShape(12.0D, 0.0D, 0.0D, 16.0D, 16.0D, 4.0D);
    private static final VoxelShape BOTTOMLEFT = Block.makeCuboidShape(0.0D, 0.0D, 12.0D, 4.0D, 16.0D, 16.0D);
    private static final VoxelShape BOTTOMRIGHT = Block.makeCuboidShape(12.0D, 0.0D, 12.0D, 16.0D, 16.0D, 16.0D);

    private static final VoxelShape TOP_SOUTH = Block.makeCuboidShape(4.0D, 13.5D, 12.0D, 12.0D, 16.0D, 16.0D);
    private static final VoxelShape TOP_WEST = Block.makeCuboidShape(0.0D, 13.5D, 4.0D, 4.0D, 16.0D, 12.0D);
    private static final VoxelShape TOP_NORTH = Block.makeCuboidShape(4.0D, 13.5D, 0.0D, 12.0D, 16.0D, 4.0D);
    private static final VoxelShape TOP_EAST = Block.makeCuboidShape(12.0D, 13.5D, 4.0D, 16.0D, 16.0D, 12.0D);

    private static final VoxelShape BOTTOM_SOUTH = Block.makeCuboidShape(4.0D, 0.0D, 12.0D, 12.0D, 2.5D, 16.0D);
    private static final VoxelShape BOTTOM_WEST = Block.makeCuboidShape(0.0D, 0.0D, 4.0D, 4.0D, 2.5D, 12.0D);
    private static final VoxelShape BOTTOM_NORTH = Block.makeCuboidShape(4.0D, 0.0D, 0.0D, 12.0D, 2.5D, 4.0D);
    private static final VoxelShape BOTTOM_EAST = Block.makeCuboidShape(12.0D, 0.0D, 4.0D, 16.0D, 2.5D, 12.0D);

    private static final VoxelShape TOP_SHAPE = VoxelShapes.or(TOPLEFT, TOPRIGHT);
    private static final VoxelShape BOTTOM_SHAPE = VoxelShapes.or(BOTTOMLEFT, BOTTOMRIGHT);
    private static final VoxelShape SHAPE = VoxelShapes.or(TOP_SHAPE, BOTTOM_SHAPE);

    private static final VoxelShape UP_NESW = VoxelShapes.or(TOP_NORTH, VoxelShapes.or(TOP_EAST, VoxelShapes.or(TOP_SOUTH, TOP_WEST)));
    private static final VoxelShape DOWN_NESW = VoxelShapes.or(BOTTOM_NORTH, VoxelShapes.or(BOTTOM_EAST, VoxelShapes.or(BOTTOM_SOUTH, BOTTOM_WEST)));

    private static final VoxelShape UP_SHAPE = VoxelShapes.or(SHAPE, UP_NESW);
    private static final VoxelShape DOWN_SHAPE = VoxelShapes.or(SHAPE, DOWN_NESW);
    private static final VoxelShape UPDOWN_SHAPE = VoxelShapes.or(SHAPE, VoxelShapes.or(UP_NESW, DOWN_NESW));

    public WindowSmall(Properties properties) {
        super(properties);
        this.setDefaultState((this.stateContainer.getBaseState()).with(UP,false).with(DOWN,false).with(WATERLOGGED, false));

    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return getShape(state);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return getShape(state);
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return getShape(state);
    }

    private VoxelShape getShape(BlockState state){
        if (state.get(DOWN) == true && state.get(UP) == true) {
            return SHAPE;
        } else if (state.get(DOWN) == false && state.get(UP) == true) {
            return DOWN_SHAPE;
        } else if (state.get(DOWN) == true && state.get(UP) == false) {
            return UP_SHAPE;
        } else {
            return UPDOWN_SHAPE;
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, WATERLOGGED);
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
        return super.getStateForPlacement(context)
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
        return block != Blocks.BARRIER && (!(block != this && !(block instanceof WindowSmall)));
    }

    private boolean canConnectTo(IWorld worldIn, BlockPos pos) {
        BlockState BlockState = worldIn.getBlockState(pos);
        Block block = BlockState.getBlock();
        return block != Blocks.BARRIER && (!(block != this && !(block instanceof WindowSmall)));
    }

    @Override
    public IFluidState getFluidState(BlockState state) {
        return Waterloggable.getFluidState(state);
    }
}
