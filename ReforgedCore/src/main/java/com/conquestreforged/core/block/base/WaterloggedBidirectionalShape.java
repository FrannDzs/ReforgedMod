package com.conquestreforged.core.block.base;

import com.conquestreforged.core.block.properties.BidirectionalShape;
import com.conquestreforged.core.block.properties.Waterloggable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;

import javax.annotation.Nonnull;

/**
 * A directional, non full-cube shape that can be waterlogged
 */
public abstract class WaterloggedBidirectionalShape extends Shape implements Waterloggable {

    public static final EnumProperty DIRECTION = EnumProperty.create("direction", BidirectionalShape.class);

    public WaterloggedBidirectionalShape(Properties builder) {
        super(builder);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        if (state.getValue(DIRECTION) == BidirectionalShape.NORTH_SOUTH) {
            return state.setValue(DIRECTION, BidirectionalShape.EAST_WEST);
        } else {
            return state.setValue(DIRECTION, BidirectionalShape.NORTH_SOUTH);
        }
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state;
    }

    @Nonnull
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BidirectionalShape facing = BidirectionalShape.EAST_WEST;
        if (context.getHorizontalDirection() == Direction.NORTH || context.getHorizontalDirection() == Direction.SOUTH) {
            facing = BidirectionalShape.NORTH_SOUTH;
        }
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

    protected void addProperties(StateContainer.Builder<Block, BlockState> builder) {}
}
