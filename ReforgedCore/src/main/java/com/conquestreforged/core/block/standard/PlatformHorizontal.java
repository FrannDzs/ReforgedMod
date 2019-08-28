package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.util.BlockRenderLayer;

@Assets(
        state = @State(name = "%s_platform", template = "parent_platform_vertical"),
        item = @Model(name = "item/%s_platform", parent = "block/%s_platform_vertical", template = "item/acacia_stairs"),
        block = {
                @Model(name = "block/%s_platform_vertical", template = "block/parent_platform_vertical"),
                @Model(name = "block/%s_platform_vertical_corner", template = "block/parent_platform_vertical_corner"),
                @Model(name = "block/%s_platform_bottom_inner", template = "block/parent_platform_bottom_inner"),
                @Model(name = "block/%s_platform_bottom_outer", template = "block/parent_platform_bottom_outer")
        }
)

public class PlatformHorizontal extends StairsBlock {
    public PlatformHorizontal(BlockState parent, Properties properties) {
        super(parent, properties);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}