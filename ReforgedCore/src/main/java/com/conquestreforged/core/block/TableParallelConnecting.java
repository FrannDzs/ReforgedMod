package com.conquestreforged.core.block;

import com.conquestreforged.core.block.properties.ParallelConnectionShape;
import com.conquestreforged.core.block.properties.Waterloggable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class TableParallelConnecting extends HorizontalBlock implements Waterloggable {

    protected static final VoxelShape SHAPE = Block.makeCuboidShape(1.0D, 10.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    public static final EnumProperty<ParallelConnectionShape> FORM = EnumProperty.create("shape", ParallelConnectionShape.class);

    public TableParallelConnecting(Properties properties) {
        super(properties);
        this.setDefaultState((this.stateContainer.getBaseState()).with(FORM, ParallelConnectionShape.ONE).with(HORIZONTAL_FACING, Direction.NORTH).with(WATERLOGGED, false));
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        if (attachesTo(worldIn.getWorld().getBlockState(facingPos))) {
            IBlockReader iblockreader = worldIn.getWorld();
            BlockState north = iblockreader.getBlockState(currentPos.north());
            BlockState east = iblockreader.getBlockState(currentPos.east());
            BlockState south = iblockreader.getBlockState(currentPos.south());
            BlockState west = iblockreader.getBlockState(currentPos.west());

            int counter = 0;

            //prevents crash when trying to update facing property
            if (facing == Direction.UP || facing == Direction.DOWN) {
                facing = stateIn.get(HORIZONTAL_FACING);
            }

            if (attachesTo(north)) {
                counter += 1;
                facing = Direction.NORTH;
            }
            if (attachesTo(east)) {
                counter += 1;
                facing = Direction.EAST;
            }
            if (attachesTo(south)) {
                counter += 1;
                facing = Direction.SOUTH;
            }
            if (attachesTo(west)) {
                counter += 1;
                facing = Direction.WEST;
            }

            if (counter == 0) {
                return stateIn
                        .with(HORIZONTAL_FACING, facing)
                        .with(FORM, ParallelConnectionShape.ONE);
            } else if (counter == 1) {
                return stateIn
                        .with(HORIZONTAL_FACING, facing)
                        .with(FORM, ParallelConnectionShape.EDGE);
            } else if (counter >= 2) {
                return stateIn
                        .with(HORIZONTAL_FACING, facing)
                        .with(FORM, ParallelConnectionShape.MIDDLE);
            } else {
                return stateIn;
            }
        }
        return stateIn;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }


    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());

        BlockPos pos = context.getPos();
        IBlockReader iblockreader = context.getWorld();
        BlockState north = iblockreader.getBlockState(pos.north());
        BlockState east = iblockreader.getBlockState(pos.east());
        BlockState south = iblockreader.getBlockState(pos.south());
        BlockState west = iblockreader.getBlockState(pos.west());

        int counter = 0;
        Direction facing = context.getPlacementHorizontalFacing().getOpposite();


        if (attachesTo(north)) {
            counter += 1;
            facing = Direction.NORTH;
        }
        if (attachesTo(east)) {
            counter += 1;
            facing = Direction.EAST;
        }
        if (attachesTo(south)) {
            counter += 1;
            facing = Direction.SOUTH;
        }
        if (attachesTo(west)) {
            counter += 1;
            facing = Direction.WEST;
        }

        if (counter == 0) {
            return this.getDefaultState()
                    .with(HORIZONTAL_FACING, facing)
                    .with(FORM, ParallelConnectionShape.ONE)
                    .with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
        } else if (counter == 1) {
            return this.getDefaultState()
                    .with(HORIZONTAL_FACING, facing)
                    .with(FORM, ParallelConnectionShape.EDGE)
                    .with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
        } else {
            return this.getDefaultState()
                    .with(HORIZONTAL_FACING, facing)
                    .with(FORM, ParallelConnectionShape.MIDDLE)
                    .with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
        }

    }

    @Override
    public IFluidState getFluidState(BlockState state) {
        return Waterloggable.getFluidState(state);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, FORM, WATERLOGGED);
    }
    private boolean attachesTo(BlockState state) {
        Block block = state.getBlock();
        return block instanceof TableParallelConnecting;
    }
}
