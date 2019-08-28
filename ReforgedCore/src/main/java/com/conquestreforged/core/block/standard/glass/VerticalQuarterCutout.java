package com.conquestreforged.core.block.standard.glass;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.standard.VerticalQuarter;
import net.minecraft.util.BlockRenderLayer;

@Assets(
        state = @State(name = "%s_vertical_quarter", template = "parent_vertical_quarter"),
        item = @Model(name = "item/%s_vertical_quarter", parent = "block/%s_vertical_quarter", template = "item/parent_vertical_quarter"),
        block = {
                @Model(name = "block/%s_vertical_quarter", template = "block/parent_vertical_quarter"),
        }
)
public class VerticalQuarterCutout extends VerticalQuarter {

    public VerticalQuarterCutout(Properties properties) {
        super(properties);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
