package com.conquestreforged.core.block;

import com.conquestreforged.core.block.standard.Cover;
import net.minecraft.util.BlockRenderLayer;

public class CoverCutout extends Cover {

    public CoverCutout(Properties properties) {
        super(properties);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

}
