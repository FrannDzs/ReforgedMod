package com.conquestreforged.core.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class Door extends DoorBlock {

    public Door(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader reader, BlockPos pos) {
        BlockState stateDown = reader.getBlockState(pos.down());
        if (state.get(HALF) == DoubleBlockHalf.LOWER) {
            return true;
        } else {
            return stateDown.getBlock() == this;
        }
    }
}
