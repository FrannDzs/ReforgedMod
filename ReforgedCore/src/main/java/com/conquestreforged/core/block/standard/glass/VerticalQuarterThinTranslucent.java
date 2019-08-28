package com.conquestreforged.core.block.standard.glass;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.standard.VerticalQuarterThin;
import net.minecraft.util.BlockRenderLayer;

@Assets(
        state = @State(name = "%s_thin_vertical_quarter", template = "parent_vertical_quarter_thin"),
        item = @Model(name = "item/%s_thin_vertical_quarter", parent = "block/%s_vertical_quarter_thin", template = "item/parent_vertical_quarter_thin"),
        block = {
                @Model(name = "block/%s_vertical_quarter_thin", template = "block/parent_vertical_quarter_thin"),
        }
)
public class VerticalQuarterThinTranslucent extends VerticalQuarterThin {

    public VerticalQuarterThinTranslucent(Properties properties) {
        super(properties);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
}
