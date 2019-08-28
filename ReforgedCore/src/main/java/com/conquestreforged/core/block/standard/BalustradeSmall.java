package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import net.minecraft.block.WallBlock;

@Assets(
        state = @State(name = "%s_small_balustrade", template = "acacia_fence"),
        item = @Model(name = "item/%s_small_balustrade", parent = "block/%s_balustrade_small_inventory", template = "item/acacia_fence"),
        block = {
                @Model(name = "block/%s_balustrade_small_post", template = "block/acacia_fence_post"),
                @Model(name = "block/%s_balustrade_small_side", template = "block/acacia_fence_side"),
                @Model(name = "block/%s_balustrade_small_inventory", template = "block/acacia_fence_inventory"),
        }
)
public class BalustradeSmall extends WallBlock {

    public BalustradeSmall(Properties properties) {
        super(properties);
    }

}
