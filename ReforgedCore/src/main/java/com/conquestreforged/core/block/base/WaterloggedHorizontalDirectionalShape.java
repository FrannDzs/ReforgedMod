package com.conquestreforged.core.block.base;

import com.conquestreforged.core.block.properties.Waterloggable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;

import javax.annotation.Nonnull;

/**
 * A directional, non full-cube shape that can be waterlogged
 */
public abstract class WaterloggedHorizontalDirectionalShape extends Shape implements Waterloggable {

    public static final DirectionProperty DIRECTION = BlockStateProperties.HORIZONTAL_FACING;

    public WaterloggedHorizontalDirectionalShape(Properties builder) {
        super(builder);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(DIRECTION, rot.rotate(state.getValue(DIRECTION)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.setValue(DIRECTION, mirrorIn.mirror(state.getValue(DIRECTION)));
    }

    @Nonnull
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction facing = context.getHorizontalDirection().getOpposite();
        FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());
        return super.getStateForPlacement(context)
                .setValue(DIRECTION, facing)
                .setValue(WATERLOGGED, fluid.getType() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected final void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(DIRECTION, WATERLOGGED);
        addProperties(builder);
    }

    protected void addProperties(StateContainer.Builder<Block, BlockState> builder) {

    }
}
