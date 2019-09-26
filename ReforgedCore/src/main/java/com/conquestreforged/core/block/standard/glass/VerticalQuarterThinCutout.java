package com.conquestreforged.core.block.standard.glass;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.standard.VerticalQuarterThin;
import net.minecraft.block.BlockState;
import net.minecraft.block.GlassBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@Assets(
        state = @State(name = "%s_thin_vertical_quarter", template = "parent_vertical_quarter_thin"),
        item = @Model(name = "item/%s_thin_vertical_quarter", parent = "block/%s_vertical_quarter_thin", template = "item/parent_vertical_quarter_thin"),
        block = {
                @Model(name = "block/%s_vertical_quarter_thin", template = "block/parent_vertical_quarter_thin"),
        }
)
public class VerticalQuarterThinCutout extends VerticalQuarterThin {

    public VerticalQuarterThinCutout(Properties properties) {
        super(properties);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) {
        if (side == Direction.UP || side == Direction.DOWN) {
            if (adjacentBlockState.getBlock() instanceof GlassBlock) {
                return true;
            } else if ((adjacentBlockState.getBlock() instanceof VerticalQuarterCutout || adjacentBlockState.getBlock() instanceof VerticalQuarterThinCutout) && (state.get(DIRECTION) == adjacentBlockState.get(DIRECTION))) {
                return true;
            } else if ((adjacentBlockState.getBlock() instanceof VerticalSlabCutout || adjacentBlockState.getBlock() instanceof VerticalSlabThinCutout) && (state.get(DIRECTION) == adjacentBlockState.get(DIRECTION) || state.get(DIRECTION) == adjacentBlockState.get(DIRECTION).rotateY())) {
                return true;
            } else if ((adjacentBlockState.getBlock() instanceof VerticalCornerCutout || adjacentBlockState.getBlock() instanceof VerticalCornerThinCutout) && (state.get(DIRECTION) == adjacentBlockState.get(DIRECTION) || state.get(DIRECTION) == adjacentBlockState.get(DIRECTION).rotateY() || state.get(DIRECTION) == adjacentBlockState.get(DIRECTION).rotateYCCW())) {
                return true;
            } else {
                return false;
            }
        }
        if (side == state.get(DIRECTION) || side == state.get(DIRECTION).rotateYCCW() ) {
            return false;
        } else if (adjacentBlockState.getBlock() instanceof GlassBlock) {
            return true;
        } else if ((adjacentBlockState.getBlock() instanceof VerticalSlabCutout || adjacentBlockState.getBlock() instanceof VerticalSlabThinCutout) && (((state.get(DIRECTION).getOpposite() == adjacentBlockState.get(DIRECTION) || state.get(DIRECTION) == adjacentBlockState.get(DIRECTION).rotateY()) && side == state.get(DIRECTION).getOpposite()) || ((state.get(DIRECTION) == adjacentBlockState.get(DIRECTION) || state.get(DIRECTION) == adjacentBlockState.get(DIRECTION).rotateYCCW()) && side == state.get(DIRECTION).rotateY()))) {
            return true;
        } else if ((adjacentBlockState.getBlock() instanceof VerticalCornerCutout || adjacentBlockState.getBlock() instanceof VerticalCornerThinCutout) && ((adjacentBlockState.get(DIRECTION) != state.get(DIRECTION).rotateY() && side == state.get(DIRECTION).getOpposite()) || (adjacentBlockState.get(DIRECTION) != state.get(DIRECTION).rotateYCCW() && side == state.get(DIRECTION).rotateY()))) {
            return true;
        } else if ((adjacentBlockState.getBlock() instanceof VerticalQuarterCutout || adjacentBlockState.getBlock() instanceof VerticalQuarterThinCutout) && ((adjacentBlockState.get(DIRECTION) == state.get(DIRECTION).rotateYCCW() && side == state.get(DIRECTION).getOpposite()) || (adjacentBlockState.get(DIRECTION) == state.get(DIRECTION).rotateY() && side == state.get(DIRECTION).rotateY()))) {
            return true;
        }
        return super.isSideInvisible(state, adjacentBlockState, side);
    }
}
