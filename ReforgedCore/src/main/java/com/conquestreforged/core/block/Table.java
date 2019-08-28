package com.conquestreforged.core.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FourWayBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class Table extends FourWayBlock {

    public Table(Properties properties) {
        super(0.0F, 0.0F, 16.0F, 16.0F, 16.0F, properties);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.fullCube();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.fullCube();
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return VoxelShapes.fullCube();
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState state1, IWorld world, BlockPos pos, BlockPos pos1) {
        if (state.get(WATERLOGGED)) {
            world.getPendingFluidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return facing.getAxis().getPlane() == Direction.Plane.HORIZONTAL ? state.with(FACING_TO_PROPERTY_MAP.get(facing), this.canTableConnectTo(world, pos, facing)) : super.updatePostPlacement(state, facing, state1, world, pos, pos1);
    }

    private boolean canTableConnectTo(IBlockReader reader, BlockPos pos, Direction facing) {
        BlockPos offset = pos.offset(facing);
        BlockState other = reader.getBlockState(offset);
        return other.canBeConnectedTo(reader, offset, facing.getOpposite()) || this.getDefaultState().canBeConnectedTo(reader, pos, facing);
    }

    @Override
    public boolean canBeConnectedTo(BlockState state, IBlockReader reader, BlockPos pos, Direction facing) {
        BlockState other = reader.getBlockState(pos.offset(facing));
        return this.attachesTo(other);
    }

    private boolean attachesTo(BlockState state) {
        Block block = state.getBlock();
        return block == this;
    }
}
