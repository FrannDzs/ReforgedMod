package com.conquestreforged.core.block;

import net.minecraft.block.AnvilBlock;
import net.minecraft.util.BlockRenderLayer;

public class Anvil extends AnvilBlock {
    public Anvil(Properties builder) {
        super(builder);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

}
