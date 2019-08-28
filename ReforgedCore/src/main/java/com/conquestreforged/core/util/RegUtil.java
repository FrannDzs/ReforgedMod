package com.conquestreforged.core.util;

import net.minecraft.block.Block;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegUtil {

    public static <T extends IForgeRegistryEntry<T>> T get(IForgeRegistry<T> registry, String id) {
        return registry.getValue(new ResourceLocation(id));
    }

    public static Item item(String name) {
        return get(ForgeRegistries.ITEMS, name);
    }

    public static Block block(String name) {
        return get(ForgeRegistries.BLOCKS, name);
    }

    public static PaintingType art(String name) {
        return get(ForgeRegistries.PAINTING_TYPES, name);
    }
}
