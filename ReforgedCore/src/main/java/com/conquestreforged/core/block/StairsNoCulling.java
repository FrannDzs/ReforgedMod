package com.conquestreforged.core.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.util.BlockRenderLayer;

public class StairsNoCulling extends StairsBlock {

    public StairsNoCulling(BlockState parent, Properties properties) {
        super(parent, properties);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}