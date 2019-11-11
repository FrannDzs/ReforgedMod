package com.conquestreforged.core.block.standard.glass;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.builder.Props;
import net.minecraft.util.BlockRenderLayer;

@Assets(
        state = @State(name = "%s_vertical_corner", template = "parent_vertical_corner_transparent"),
        item = @Model(name = "item/%s_vertical_corner", parent = "block/%s_vertical_corner_4", template = "item/parent_vertical_corner"),
        block = {
                @Model(name = "block/%s_vertical_corner_2", template = "block/parent_vertical_corner_transparent_2"),
                @Model(name = "block/%s_vertical_corner_4", template = "block/parent_vertical_corner_transparent_4"),
                @Model(name = "block/%s_vertical_corner_6", template = "block/parent_vertical_corner_transparent_6"),
        }
)
public class VerticalCornerTranslucent extends VerticalCornerCutout {

    public VerticalCornerTranslucent(Props properties) {
        super(properties);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

}
