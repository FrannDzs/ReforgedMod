package com.conquestreforged.core.item.group;

import net.minecraft.item.ItemStack;

import java.util.Comparator;
import java.util.Map;

public class ItemStackSorter implements Comparator<ItemStack> {

    private final Map<String, Integer> index;
    private final int unspecified;

    public ItemStackSorter(Map<String, Integer> index) {
        this.index = index;
        this.unspecified = index.size();
    }

    @Override
    public int compare(ItemStack o1, ItemStack o2) {
        String s1 = o1.getItem().getRegistryName() + "";
        String s2 = o2.getItem().getRegistryName() + "";
        int i1 = index.getOrDefault(s1, unspecified);
        int i2 = index.getOrDefault(s2, unspecified);
        return Integer.compare(i1, i2);
    }
}
