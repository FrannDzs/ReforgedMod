package com.conquestreforged.core.item.group.sort;

import net.minecraft.item.ItemStack;

import java.util.Comparator;
import java.util.List;

public class SimpleSorter implements Sorter<ItemStack> {

    private final Comparator<ItemStack> comparator;

    public SimpleSorter(Comparator<ItemStack> comparator) {
        this.comparator = comparator;
    }

    @Override
    public void apply(List<ItemStack> list) {
        list.sort(comparator);
    }
}
