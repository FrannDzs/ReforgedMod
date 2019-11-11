package com.conquestreforged.core.block.standard.glass;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.builder.Props;
import com.conquestreforged.core.block.standard.VerticalQuarter;
import net.minecraft.block.BlockState;
import net.minecraft.block.GlassBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@Assets(
        state = @State(name = "%s_vertical_quarter", template = "parent_vertical_quarter"),
        item = @Model(name = "item/%s_vertical_quarter", parent = "block/%s_vertical_quarter_4", template = "item/parent_vertical_quarter"),
        block = {
                @Model(name = "block/%s_vertical_quarter_2", template = "block/parent_vertical_quarter_2"),
                @Model(name = "block/%s_vertical_quarter_4", template = "block/parent_vertical_quarter_4"),
                @Model(name = "block/%s_vertical_quarter_6", template = "block/parent_vertical_quarter_6"),
        }
)
public class VerticalQuarterCutout extends VerticalQuarter {

    public VerticalQuarterCutout(Props properties) {
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
            } else if (adjacentBlockState.getBlock() instanceof VerticalQuarterCutout && adjacentBlockState.get(LAYERS) >= state.get(LAYERS) && (state.get(DIRECTION) == adjacentBlockState.get(DIRECTION))) {
                return true;
            } else if (adjacentBlockState.getBlock() instanceof VerticalSlabCutout && adjacentBlockState.get(LAYERS) >= state.get(LAYERS) && (state.get(DIRECTION) == adjacentBlockState.get(DIRECTION) || state.get(DIRECTION) == adjacentBlockState.get(DIRECTION).rotateY())) {
                return true;
            } else if (adjacentBlockState.getBlock() instanceof VerticalCornerCutout && adjacentBlockState.get(LAYERS) >= state.get(LAYERS) && (state.get(DIRECTION) == adjacentBlockState.get(DIRECTION) || state.get(DIRECTION) == adjacentBlockState.get(DIRECTION).rotateY() || state.get(DIRECTION) == adjacentBlockState.get(DIRECTION).rotateYCCW())) {
                return true;
            } else {
                return false;
            }
        }
        if (side == state.get(DIRECTION) || side == state.get(DIRECTION).rotateYCCW() ) {
            return false;
        } else if (adjacentBlockState.getBlock() instanceof GlassBlock) {
            return true;
        } else if (adjacentBlockState.getBlock() instanceof VerticalSlabCutout && adjacentBlockState.get(LAYERS) >= state.get(LAYERS) && (((state.get(DIRECTION).getOpposite() == adjacentBlockState.get(DIRECTION) || state.get(DIRECTION) == adjacentBlockState.get(DIRECTION).rotateY()) && side == state.get(DIRECTION).getOpposite()) || ((state.get(DIRECTION) == adjacentBlockState.get(DIRECTION) || state.get(DIRECTION) == adjacentBlockState.get(DIRECTION).rotateYCCW()) && side == state.get(DIRECTION).rotateY()))) {
            return true;
        } else if (adjacentBlockState.getBlock() instanceof VerticalCornerCutout && adjacentBlockState.get(LAYERS) >= state.get(LAYERS) && ((adjacentBlockState.get(DIRECTION) != state.get(DIRECTION).rotateY() && side == state.get(DIRECTION).getOpposite()) || (adjacentBlockState.get(DIRECTION) != state.get(DIRECTION).rotateYCCW() && side == state.get(DIRECTION).rotateY()))) {
            return true;
        } else if (adjacentBlockState.getBlock() instanceof VerticalQuarterCutout && adjacentBlockState.get(LAYERS) >= state.get(LAYERS) && ((adjacentBlockState.get(DIRECTION) == state.get(DIRECTION).rotateYCCW() && side == state.get(DIRECTION).getOpposite()) || (adjacentBlockState.get(DIRECTION) == state.get(DIRECTION).rotateY() && side == state.get(DIRECTION).rotateY()))) {
            return true;
        }
        return super.isSideInvisible(state, adjacentBlockState, side);
    }
}
