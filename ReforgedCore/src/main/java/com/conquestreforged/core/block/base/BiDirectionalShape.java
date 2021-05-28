package com.conquestreforged.core.block.base;

import com.conquestreforged.core.block.properties.BidirectionalShape;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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
public abstract class BiDirectionalShape extends Shape {

    public static final EnumProperty DIRECTION = EnumProperty.create("direction", BidirectionalShape.class);

    public BiDirectionalShape(Properties builder) {
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
        return super.getStateForPlacement(context).setValue(DIRECTION, facing);
    }

    @Override
    protected final void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(DIRECTION);
        addProperties(builder);
    }

    protected void addProperties(StateContainer.Builder<Block, BlockState> builder) {

    }
}
