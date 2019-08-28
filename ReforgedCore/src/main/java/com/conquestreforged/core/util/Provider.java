package com.conquestreforged.core.util;

import com.google.common.base.Preconditions;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

/**
 * Used to provide references to objects that can be used during initialization
 * with out creating circular dependency issues (ie Block constructors that rely on
 * Items and vice-versa
 */
public class Provider<T extends IItemProvider> implements IItemProvider {

    private final Supplier<T> supplier;
    private final Supplier<T> defaultValue;

    private T value;

    private Provider(Supplier<T> supplier, Supplier<T> defaultValue) {
        this.supplier = supplier;
        this.defaultValue = defaultValue;
    }

    public T get() {
        if (value == null) {
            T t = supplier.get();
            value = t == null ? defaultValue.get() : value;
            Preconditions.checkNotNull(value);
        }
        return value;
    }

    @Override
    public net.minecraft.item.Item asItem() {
        return get().asItem();
    }

    public static Provider.Block block(String name) {
        return block(new ResourceLocation(name));
    }

    public static Provider.Block block(ResourceLocation name) {
        return block(() -> ForgeRegistries.BLOCKS.getValue(name));
    }

    public static Provider.Block block(Supplier<net.minecraft.block.Block> getter) {
        return new Block(getter);
    }

    public static Provider.Item item(String name) {
        return item(new ResourceLocation(name));
    }

    public static Provider.Item item(ResourceLocation name) {
        return item(() -> ForgeRegistries.ITEMS.getValue(name));
    }

    public static Provider.Item item(Supplier<net.minecraft.item.Item> getter) {
        return new Item(getter);
    }

    public static class Block extends Provider<net.minecraft.block.Block> {
        public Block(Supplier<net.minecraft.block.Block> supplier) {
            super(supplier, () -> Blocks.AIR);
        }
    }

    public static class Item extends Provider<net.minecraft.item.Item> {
        public Item(Supplier<net.minecraft.item.Item> supplier) {
            super(supplier, () -> Items.AIR);
        }
    }
}
