package com.conquestreforged.core.block;

import net.minecraft.block.CauldronBlock;
import net.minecraft.util.BlockRenderLayer;

public class Cauldron extends CauldronBlock {

    public Cauldron(Properties properties) {
        super(properties);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

}
