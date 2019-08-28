package com.conquestreforged.core.block;

import com.conquestreforged.core.block.standard.VerticalSlab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class Rack extends VerticalSlab {

    public Rack(Properties properties) {
        super(properties);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    private boolean attachesTo(BlockState blockstate) {
        Block block = blockstate.getBlock();
        return !Block.cannotAttach(block) && (!(block != this && !(block instanceof Rack)));
    }

    private boolean canConnectTo(IWorld worldIn, BlockPos pos) {
        BlockState BlockState = worldIn.getBlockState(pos);
        Block block = BlockState.getBlock();
        return !Block.cannotAttach(block) && (!(block != this && !(block instanceof Rack)));
    }
}
