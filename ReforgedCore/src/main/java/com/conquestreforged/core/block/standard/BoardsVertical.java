package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.*;
import net.minecraft.util.BlockRenderLayer;

@Assets(
        state = @State(name = "%s_boards", template = "parent_boards_vertical"),
        item = @Model(name = "item/%s_boards", parent = "block/%s_boards_lower", template = "item/acacia_slab"),
        block = {
                @Model(name = "block/%s_boards_lower", template = "block/parent_boards_vertical_lower"),
                @Model(name = "block/%s_boards_upper", template = "block/parent_boards_vertical_upper"),
        },
        recipe = @Recipe(
                name = "%s_boards",
                template = "acacia_slab",
                ingredients = {
                        @Ingredient(name = "%s", template = "acacia_planks", plural = true)
                }
        )
)
public class BoardsVertical extends SlabDirectional {

    public BoardsVertical(Properties properties) {
        super(properties);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}