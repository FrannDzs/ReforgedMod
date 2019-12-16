package com.conquestreforged.core.block;

import com.conquestreforged.core.block.properties.Waterloggable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class TorchFlat extends Block implements Waterloggable {

    public static final DirectionProperty DIRECTION = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty LIGHT_0_3 = IntegerProperty.create("light", 0, 3);
    protected static final VoxelShape AABB = Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    public TorchFlat(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(DIRECTION, Direction.NORTH).with(LIGHT_0_3, 0).with(WATERLOGGED, false));

    }

    @Deprecated
    public int getLightValue(BlockState state) {
        if (state.get(LIGHT_0_3) == 0) {
            return 0;
        } else if (state.get(LIGHT_0_3) == 1) {
            return 5;
        } else if (state.get(LIGHT_0_3) == 2) {
            return 10;
        } else {
            return 14;
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(DIRECTION, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        return AABB;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(DIRECTION, LIGHT_0_3, WATERLOGGED);
    }

    @Override
    public IFluidState getFluidState(BlockState state) {
        return Waterloggable.getFluidState(state);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }
}
