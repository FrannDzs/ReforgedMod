package com.conquestreforged.core.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class Stack {

    public static Supplier<ItemStack> block(String name) {
        return block(new ResourceLocation(name));
    }

    public static Supplier<ItemStack> block(ResourceLocation name) {
        return () -> new ItemStack(ForgeRegistries.BLOCKS.getValue(name));
    }

    public static Supplier<ItemStack> item(String name) {
        return item(new ResourceLocation(name));
    }

    public static Supplier<ItemStack> item(ResourceLocation name) {
        return () -> new ItemStack(ForgeRegistries.ITEMS.getValue(name));
    }
}
