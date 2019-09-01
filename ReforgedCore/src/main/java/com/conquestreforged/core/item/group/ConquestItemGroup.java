package com.conquestreforged.core.item.group;

import com.conquestreforged.core.init.Context;
import com.conquestreforged.core.item.family.FamilyGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public abstract class ConquestItemGroup extends ItemGroup {

    private static final String pathFormat = "/assets/%s/groups/%s.txt";

    private final int index;
    private final String label;
    private final String namespace;
    private Comparator<ItemStack> order = null;
    private List<ItemStack> cached = Collections.emptyList();

    public ConquestItemGroup(int index, String label) {
        super(-1, label);
        this.index = index;
        this.label = label;
        this.namespace = Context.getInstance().getNamespace();
        loadOrder();
    }

    public void setOrder(Comparator<ItemStack> order) {
        this.order = order;
        invalidate();
    }

    public void invalidate() {
        cached = Collections.emptyList();
    }

    public int getOrderIndex() {
        return index;
    }

    public void loadOrder() {
        String path = String.format(pathFormat, namespace, label);
        try (InputStream in = FamilyGroup.class.getResourceAsStream(path)) {
            if (in != null) {
                setOrder(ConquestItemGroup.sorter(in));
            }
        } catch (IOException e) {
            // errors if unable to close the resource or reading the stream fails
            e.printStackTrace();
        }
    }

    @Override
    public final void fill(NonNullList<ItemStack> items) {
        if (cached.isEmpty()) {
            NonNullList<ItemStack> list = NonNullList.create();
            populate(list);
            cached = new ArrayList<>(list);
            if (order != null) {
                cached.sort(order);
            }
        }
        items.addAll(cached);
    }

    public abstract void populate(NonNullList<ItemStack> items);

    public static Comparator<ItemStack> sorter(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int index = 0;
            Map<String, Integer> order = new HashMap<>();
            while ((line = reader.readLine()) != null) {
                order.put(line, index++);
            }
            return new ItemStackSorter(order);
        }
    }
}
