package com.conquestreforged.core.item.group.sort;

import com.conquestreforged.core.util.Provider;
import net.minecraft.item.ItemStack;

import java.io.BufferedReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ItemList implements Sorter<ItemStack>, Comparator<ItemStack> {

    private final Map<String, Entry> index;

    private ItemList(Map<String, Entry> index) {
        this.index = index;
    }

    @Override
    public int compare(ItemStack o1, ItemStack o2) {
        int i1 = getIndex(o1, index);
        int i2 = getIndex(o2, index);
        return Integer.compare(i1, i2);
    }

    @Override
    public void apply(List<ItemStack> items) {
        fill(items);
        items.sort(this);
    }

    private void fill(List<ItemStack> items) {
        for (Map.Entry<String, Entry> e : index.entrySet()) {
            if (!contains(items, e.getKey())) {
                e.getValue().stack.getSafely().ifPresent(items::add);
            }
        }
    }

    private static int getIndex(ItemStack stack, Map<String, Entry> index) {
        String name = stack.getItem().getRegistryName() + "";
        Entry entry = index.get(name);
        if (entry == null) {
            return index.size();
        }
        return entry.index;
    }

    private static boolean contains(List<ItemStack> items, String find) {
        for (ItemStack stack : items) {
            if ((stack.getItem().getRegistryName() + "").equals(find)) {
                return true;
            }
        }
        return false;
    }

    private static class Entry {

        private final int index;
        private final Provider.Stack stack;

        private Entry(int index, Provider<?> provider) {
            this.index = index;
            this.stack = provider.toStack();
        }
    }

    public static ItemList read(BufferedReader reader) {
        AtomicInteger order = new AtomicInteger(0);
        Map<String, Entry> index = new HashMap<>(50);
        reader.lines().forEach(item -> {
            if (item.isEmpty()) {
                return;
            }
            index.put(item, new Entry(order.get(), Provider.item(item)));
            order.addAndGet(1);
        });
        return new ItemList(index);
    }
}
