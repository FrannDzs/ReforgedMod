package com.conquestreforged.core.block.base;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;

import javax.annotation.Nonnull;

public abstract class HorizontalDirectionalShape extends Shape {

    public static final DirectionProperty DIRECTION = BlockStateProperties.HORIZONTAL_FACING;

    public HorizontalDirectionalShape(Properties builder) {
        super(builder);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(DIRECTION, rot.rotate(state.getValue(DIRECTION)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(DIRECTION)));
    }

    @Nonnull
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction facing = context.getHorizontalDirection().getOpposite();
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
