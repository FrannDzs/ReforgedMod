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
import net.minecraft.block.GlassBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@Assets(
        state = @State(name = "%s_vertical_corner", template = "parent_vertical_corner_transparent"),
        item = @Model(name = "item/%s_vertical_corner", parent = "block/%s_vertical_corner", template = "item/parent_vertical_corner"),
        block = {
                @Model(name = "block/%s_vertical_corner", template = "block/parent_vertical_corner_transparent"),
        }
)
public class VerticalCornerCutout extends VerticalCorner {

    public VerticalCornerCutout(Properties properties) {
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
            if ( adjacentBlockState.getBlock() instanceof GlassBlock || (adjacentBlockState.getBlock() instanceof VerticalCornerCutout && state.get(DIRECTION) == adjacentBlockState.get(DIRECTION))) {
                return true;
            }
        }

        if (state.get(DIRECTION) == Direction.EAST) {
            if (side == Direction.NORTH || side == Direction.EAST) {
                return false;
            } else if (adjacentBlockState.getBlock() instanceof AbstractGlassBlock
                    || ((adjacentBlockState.getBlock() instanceof VerticalCorner || adjacentBlockState.getBlock() instanceof VerticalCornerThin) && side == Direction.SOUTH && (adjacentBlockState.get(DIRECTION) == Direction.WEST || adjacentBlockState.get(DIRECTION) == Direction.SOUTH ))) {
                return true;
            } else if (adjacentBlockState.getBlock() instanceof AbstractGlassBlock
                    || ((adjacentBlockState.getBlock() instanceof VerticalCorner || adjacentBlockState.getBlock() instanceof VerticalCornerThin) && side == Direction.WEST && (adjacentBlockState.get(DIRECTION) == Direction.WEST || adjacentBlockState.get(DIRECTION) == Direction.SOUTH ))) {
                return true;
            } else if ((adjacentBlockState.getBlock() instanceof VerticalSlab || adjacentBlockState.getBlock() instanceof VerticalSlabThin) && side == Direction.SOUTH && adjacentBlockState.get(DIRECTION) == Direction.SOUTH) {
                return true;
            } else if ((adjacentBlockState.getBlock() instanceof VerticalSlab || adjacentBlockState.getBlock() instanceof VerticalSlabThin) && side == Direction.WEST && adjacentBlockState.get(DIRECTION) == Direction.WEST) {
                return true;
            } else {
                super.isSideInvisible(state, adjacentBlockState, side);
            }
        }

        if (state.get(DIRECTION) == Direction.WEST) {
            if (side == Direction.SOUTH || side == Direction.WEST) {
                return false;
            } else if (adjacentBlockState.getBlock() instanceof AbstractGlassBlock
                    || ((adjacentBlockState.getBlock() instanceof VerticalCorner || adjacentBlockState.getBlock() instanceof VerticalCornerThin) && side == Direction.NORTH && (adjacentBlockState.get(DIRECTION) == Direction.EAST || adjacentBlockState.get(DIRECTION) == Direction.NORTH ))) {
                return true;
            } else if (adjacentBlockState.getBlock() instanceof AbstractGlassBlock
                    || ((adjacentBlockState.getBlock() instanceof VerticalCorner || adjacentBlockState.getBlock() instanceof VerticalCornerThin) && side == Direction.EAST && (adjacentBlockState.get(DIRECTION) == Direction.EAST || adjacentBlockState.get(DIRECTION) == Direction.SOUTH ))) {
                return true;
            } else if ((adjacentBlockState.getBlock() instanceof VerticalSlab || adjacentBlockState.getBlock() instanceof VerticalSlabThin) && side == Direction.NORTH && adjacentBlockState.get(DIRECTION) == Direction.NORTH) {
                return true;
            } else if ((adjacentBlockState.getBlock() instanceof VerticalSlab || adjacentBlockState.getBlock() instanceof VerticalSlabThin) && side == Direction.EAST && adjacentBlockState.get(DIRECTION) == Direction.EAST) {
                return true;
            } else {
                super.isSideInvisible(state, adjacentBlockState, side);
            }
        }

        if (state.get(DIRECTION) == Direction.SOUTH) {
            if (side == Direction.SOUTH || side == Direction.EAST) {
                return false;
            } else if (adjacentBlockState.getBlock() instanceof AbstractGlassBlock
                    || ((adjacentBlockState.getBlock() instanceof VerticalCorner || adjacentBlockState.getBlock() instanceof VerticalCornerThin) && side == Direction.NORTH && (adjacentBlockState.get(DIRECTION) == Direction.EAST || adjacentBlockState.get(DIRECTION) == Direction.NORTH ))) {
                return true;
            } else if (adjacentBlockState.getBlock() instanceof AbstractGlassBlock
                    || ((adjacentBlockState.getBlock() instanceof VerticalCorner || adjacentBlockState.getBlock() instanceof VerticalCornerThin) && side == Direction.WEST && (adjacentBlockState.get(DIRECTION) == Direction.WEST || adjacentBlockState.get(DIRECTION) == Direction.NORTH ))) {
                return true;
            } else if ((adjacentBlockState.getBlock() instanceof VerticalSlab || adjacentBlockState.getBlock() instanceof VerticalSlabThin) && side == Direction.NORTH && adjacentBlockState.get(DIRECTION) == Direction.NORTH) {
                return true;
            } else if ((adjacentBlockState.getBlock() instanceof VerticalSlab || adjacentBlockState.getBlock() instanceof VerticalSlabThin) && side == Direction.WEST && adjacentBlockState.get(DIRECTION) == Direction.WEST) {
                return true;
            } else {
                super.isSideInvisible(state, adjacentBlockState, side);
            }
        }

        if (state.get(DIRECTION) == Direction.NORTH) {
            if (side == Direction.NORTH || side == Direction.WEST) {
                return false;
            } else if (adjacentBlockState.getBlock() instanceof AbstractGlassBlock
                    || ((adjacentBlockState.getBlock() instanceof VerticalCorner || adjacentBlockState.getBlock() instanceof VerticalCornerThin) && side == Direction.SOUTH && (adjacentBlockState.get(DIRECTION) == Direction.WEST || adjacentBlockState.get(DIRECTION) == Direction.SOUTH ))) {
                return true;
            } else if (adjacentBlockState.getBlock() instanceof AbstractGlassBlock
                    || ((adjacentBlockState.getBlock() instanceof VerticalCorner || adjacentBlockState.getBlock() instanceof VerticalCornerThin) && side == Direction.EAST && (adjacentBlockState.get(DIRECTION) == Direction.EAST || adjacentBlockState.get(DIRECTION) == Direction.SOUTH ))) {
                return true;
            } else if ((adjacentBlockState.getBlock() instanceof VerticalSlab || adjacentBlockState.getBlock() instanceof VerticalSlabThin) && side == Direction.SOUTH && adjacentBlockState.get(DIRECTION) == Direction.SOUTH) {
                return true;
            } else if ((adjacentBlockState.getBlock() instanceof VerticalSlab || adjacentBlockState.getBlock() instanceof VerticalSlabThin) && side == Direction.EAST && adjacentBlockState.get(DIRECTION) == Direction.EAST) {
                return true;
            } else {
                super.isSideInvisible(state, adjacentBlockState, side);
            }
        }
        return super.isSideInvisible(state, adjacentBlockState, side);
    }
}