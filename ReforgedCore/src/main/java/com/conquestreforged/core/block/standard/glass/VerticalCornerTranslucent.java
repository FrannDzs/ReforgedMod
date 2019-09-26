package com.conquestreforged.core.block.standard.glass;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.standard.VerticalCorner;
import com.conquestreforged.core.block.standard.VerticalCornerThin;
import com.conquestreforged.core.block.standard.VerticalSlab;
import com.conquestreforged.core.block.standard.VerticalSlabThin;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@Assets(
        state = @State(name = "%s_vertical_corner", template = "parent_vertical_corner_transparent"),
        item = @Model(name = "item/%s_vertical_corner", parent = "block/%s_vertical_corner", template = "item/parent_vertical_corner"),
        block = {
                @Model(name = "block/%s_vertical_corner", template = "block/parent_vertical_corner_transparent"),
        }
)
public class VerticalCornerTranslucent extends VerticalCornerCutout {

    public VerticalCornerTranslucent(Properties properties) {
        super(properties);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

}
