package com.conquestreforged.core.block.standard.glass;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.builder.Props;
import net.minecraft.util.BlockRenderLayer;

@Assets(
        state = @State(name = "%s_vertical_slab", template = "parent_vertical_slab"),
        item = @Model(name = "item/%s_vertical_slab", parent = "block/%s_vertical_slab_4", template = "item/parent_vertical_slab"),
        block = {
                @Model(name = "block/%s_vertical_slab_2", template = "block/parent_vertical_slab_2"),
                @Model(name = "block/%s_vertical_slab_4", template = "block/parent_vertical_slab_4"),
                @Model(name = "block/%s_vertical_slab_6", template = "block/parent_vertical_slab_6"),
        }
)
public class VerticalSlabTranslucent extends VerticalSlabCutout {

    public VerticalSlabTranslucent(Props properties) {
        super(properties);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
}
