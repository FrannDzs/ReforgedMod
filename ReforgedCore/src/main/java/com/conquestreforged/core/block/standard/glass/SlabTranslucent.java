package com.conquestreforged.core.block.standard.glass;

import com.conquestreforged.core.asset.annotation.*;
import com.conquestreforged.core.block.builder.Props;
import com.conquestreforged.core.block.standard.Slab;
import net.minecraft.util.BlockRenderLayer;

@Assets(
        state = @State(name = "%s_slab", template = "parent_slab"),
        item = @Model(name = "item/%s_slab", parent = "block/%s_slab", template = "item/acacia_slab"),
        block = {
                @Model(name = "block/%s_slab", template = "block/parent_slab"),
        },
        recipe = @Recipe(
                name = "%s_slab",
                template = "acacia_slab",
                ingredients = {
                        @Ingredient(name = "%s", template = "acacia_planks", plural = true)
                }
        )
)
public class SlabTranslucent extends Slab {

    public SlabTranslucent(Props properties) {
        super(properties);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
}
