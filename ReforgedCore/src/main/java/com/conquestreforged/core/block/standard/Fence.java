package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.*;
import net.minecraft.block.FenceBlock;

@Assets(
        state = @State(name = "%s_fence", template = "parent_fence"),
        item = @Model(name = "item/%s_fence", parent = "block/%s_fence_ns_1", template = "item/acacia_fence"),
        block = {
                @Model(name = "block/%s_fence_post", template = "block/parent_fence_post"),
                @Model(name = "block/%s_fence_n_1", template = "block/parent_fence_n_1"),
                @Model(name = "block/%s_fence_n_2", template = "block/parent_fence_n_2"),
                @Model(name = "block/%s_fence_ne_1", template = "block/parent_fence_ne_1"),
                @Model(name = "block/%s_fence_ne_2", template = "block/parent_fence_ne_2"),
                @Model(name = "block/%s_fence_ns_1", template = "block/parent_fence_ns_1"),
                @Model(name = "block/%s_fence_ns_2", template = "block/parent_fence_ns_2"),
                @Model(name = "block/%s_fence_nse", template = "block/parent_fence_nse"),
                @Model(name = "block/%s_fence_nsew", template = "block/parent_fence_nsew"),
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