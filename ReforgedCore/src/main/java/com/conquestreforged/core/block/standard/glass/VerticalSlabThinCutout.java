package com.conquestreforged.core.block.standard.glass;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.standard.VerticalSlabThin;
import net.minecraft.util.BlockRenderLayer;

@Assets(
        state = @State(name = "%s_thin_vertical_slab", template = "parent_vertical_slab_thin"),
        item = @Model(name = "item/%s_thin_vertical_slab", parent = "block/%s_vertical_slab_thin", template = "item/parent_vertical_slab_thin"),
        block = {
                @Model(name = "block/%s_vertical_slab_thin", template = "block/parent_vertical_slab_thin"),
        }
)
public class VerticalSlabThinCutout extends VerticalSlabThin {

    public VerticalSlabThinCutout(Properties properties) {
        super(properties);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
