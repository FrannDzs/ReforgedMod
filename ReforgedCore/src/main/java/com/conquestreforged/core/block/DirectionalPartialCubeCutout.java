package com.conquestreforged.core.block;

import net.minecraft.block.Block;
import net.minecraft.util.BlockRenderLayer;

public class DirectionalPartialCubeCutout extends DirectionalPartialCube {

    public DirectionalPartialCubeCutout(Block.Properties properties) {
        super(properties);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

}
