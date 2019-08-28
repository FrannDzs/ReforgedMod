package com.conquestreforged.core.block.builder;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;

public class VanillaProps {

    public static Props stone() {
        return Props.create(Blocks.STONE).group(ItemGroup.BUILDING_BLOCKS);
    }

    public static Props bricks() {
        return Props.create(Blocks.BRICKS).group(ItemGroup.BUILDING_BLOCKS);
    }

    public static Props planks() {
        return Props.create(Blocks.OAK_PLANKS).group(ItemGroup.BUILDING_BLOCKS);
    }

    public static Props logs() {
        return Props.create(Blocks.OAK_LOG).group(ItemGroup.BUILDING_BLOCKS);
    }

    public static Props grass() {
        return Props.create(Blocks.GRASS_BLOCK).group(ItemGroup.BUILDING_BLOCKS);
    }

    public static Props glass() {
        return Props.create(Blocks.GLASS).group(ItemGroup.DECORATIONS);
    }

    public static Props cloth() {
        return Props.create(Blocks.WHITE_WOOL).group(ItemGroup.DECORATIONS);
    }

    public static Props plants() {
        return Props.create(Blocks.GRASS).group(ItemGroup.DECORATIONS);
    }

    public static Props earth() {
        return Props.create(Blocks.DIRT).group(ItemGroup.BUILDING_BLOCKS);
    }

    public static Props ice() {
        return Props.create(Blocks.PACKED_ICE).group(ItemGroup.BUILDING_BLOCKS);
    }

    public static Props metal() {
        return Props.create(Blocks.IRON_BLOCK).group(ItemGroup.BUILDING_BLOCKS);
    }

    public static Props leaves() {
        return Props.create(Blocks.OAK_LEAVES).group(ItemGroup.BUILDING_BLOCKS);
    }
}
