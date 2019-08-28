package com.conquestreforged.core.block.standard.glass;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.standard.VerticalCornerThin;
import net.minecraft.util.BlockRenderLayer;

@Assets(
        state = @State(name = "%s_thin_vertical_corner", template = "parent_vertical_corner_thin"),
        item = @Model(name = "item/%s_thin_vertical_corner", parent = "block/%s_vertical_corner_thin", template = "item/parent_vertical_corner_thin"),
        block = {
                @Model(name = "block/%s_vertical_corner_thin", template = "block/parent_vertical_corner_thin"),
        }
)
public class VerticalCornerThinTranslucent extends VerticalCornerThin {

    public VerticalCornerThinTranslucent(Properties properties) {
        super(properties);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
}
