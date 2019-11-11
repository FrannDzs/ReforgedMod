package com.conquestreforged.core.block.standard.glass;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import net.minecraft.block.BlockState;
import net.minecraft.block.GlassBlock;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.conquestreforged.core.block.base.WaterloggedDirectionalShape.DIRECTION;

@Assets(
        state = @State(name = "%s", template = "parent_cube", plural = true),
        item = @Model(name = "item/%s", parent = "block/%s", template = "item/parent_cube", plural = true),
        block = @Model(name = "block/%s", template = "block/parent_cube", plural = true)
)
public class Glass extends GlassBlock {

    public Glass(Properties properties) {
        super(properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) {
        if (adjacentBlockState.getBlock() instanceof GlassBlock) {
            return true;
        } else if ((adjacentBlockState.getBlock() instanceof VerticalSlabCutout) && (adjacentBlockState.get(DIRECTION) == side)) {
            return true;
        } else if ((adjacentBlockState.getBlock() instanceof VerticalCornerCutout) && (adjacentBlockState.get(DIRECTION) == side || adjacentBlockState.get(DIRECTION).rotateYCCW() == side)) {
            return true;
        }
        return super.isSideInvisible(state, adjacentBlockState, side);
    }
}
