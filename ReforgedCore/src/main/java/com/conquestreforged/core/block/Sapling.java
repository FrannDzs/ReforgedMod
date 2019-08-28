package com.conquestreforged.core.block;

import com.conquestreforged.core.block.builder.Props;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class Sapling extends SaplingBlock {

    public Sapling(Props props) {
        super(props.get("tree", Tree.class), props.toProperties());
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }
}
