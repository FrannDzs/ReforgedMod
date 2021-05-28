package com.conquestreforged.core.block.properties;

import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;

public interface Waterloggable extends IWaterLoggable {

    BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    static FluidState getFluidState(BlockState state) {
        //todo check #getSource
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }
}
