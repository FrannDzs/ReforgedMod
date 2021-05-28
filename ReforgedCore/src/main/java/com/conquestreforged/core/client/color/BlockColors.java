package com.conquestreforged.core.client.color;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.BlockItem;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.BiomeColors;

public class BlockColors {

    public static final IBlockColor GRASS = (state, reader, pos, tint) -> {
        if (reader != null && pos != null) {
            return BiomeColors.getAverageGrassColor(reader, pos);
        }
        return defaultGrassColor();
    };

    public static final IBlockColor FOLIAGE = (state, reader, pos, tint) -> {
        if (reader != null && pos != null) {
            return BiomeColors.getAverageFoliageColor(reader, pos);
        }
        return defaultFoliageColor();
    };

    public static final IBlockColor WATER = (state, reader, pos, tint) -> {
        if (reader != null && pos != null) {
            return BiomeColors.getAverageWaterColor(reader, pos);
        }
        return defaultWaterColor();
    };

    public static IItemColor toItemColor(net.minecraft.client.renderer.color.BlockColors colors) {
        return (stack, tint) -> {
            BlockState state = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
            return colors.getColor(state, null, null, tint);
        };
    }

    /*
     * Defaults as per nmc BlockColors
     * net.minecraft.client.renderer.color.BlockColors
     */

    private static int defaultGrassColor() {
        return GrassColors.get(0.5, 1.0);
    }

    private static int defaultFoliageColor() {
        return FoliageColors.getDefaultColor();
    }

    private static int defaultWaterColor() {
        return -1;
    }
}
