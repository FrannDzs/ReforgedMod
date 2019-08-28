package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.*;
import net.minecraft.block.FenceBlock;

@Assets(
        state = @State(name = "%s_fence", template = "acacia_fence"),
        item = @Model(name = "item/%s_fence", parent = "block/%s_fence_inventory", template = "item/acacia_fence"),
        block = {
                @Model(name = "block/%s_fence_post", template = "block/acacia_fence_post"),
                @Model(name = "block/%s_fence_side", template = "block/acacia_fence_side"),
                @Model(name = "block/%s_fence_inventory", template = "block/acacia_fence_inventory"),
        },
        recipe = @Recipe(
                name = "%s_fence",
                template = "acacia_fence",
                ingredients = {
                        @Ingredient(name = "%s", template = "acacia_planks", plural = true)
                }
        )
)
public class Fence extends FenceBlock {

    public Fence(Properties properties) {
        super(properties);
    }

}