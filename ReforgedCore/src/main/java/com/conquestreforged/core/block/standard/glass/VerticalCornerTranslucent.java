package com.conquestreforged.core.block.standard.glass;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.standard.VerticalCornerThin;
import net.minecraft.util.BlockRenderLayer;

@Assets(
        state = @State(name = "%s_vertical_corner", template = "parent_vertical_corner"),
        item = @Model(name = "item/%s_vertical_corner", parent = "block/%s_vertical_corner", template = "item/parent_vertical_corner"),
        block = {
                @Model(name = "block/%s_vertical_corner", template = "block/parent_vertical_corner"),
        }
)
public class VerticalCornerTranslucent extends VerticalCornerThin {

    public VerticalCornerTranslucent(Properties properties) {
        super(properties);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
}
