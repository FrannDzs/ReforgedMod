package com.conquestreforged.core.block.factory;

import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class TypeList implements Iterable<Class<? extends Block>>, Comparator<Block> {

    public static final TypeList EMPTY = new TypeList(Collections.emptyList());

    private final List<Class<? extends Block>> types;

    private TypeList(List<Class<? extends Block>> types) {
        this.types = types;
    }

    public boolean isEmpty() {
        return types.isEmpty();
    }

    public Class<? extends Block> first() {
        if (types.size() > 0) {
            return types.get(0);
        }
        return AirBlock.class;
    }

    public static TypeList of(Collection<Class<? extends Block>> types) {
        return new TypeList(new ArrayList<>(types));
    }

    @SafeVarargs
    public static TypeList of(Class<? extends Block>... types) {
        if (types.length == 0) {
            throw new RuntimeException("No Types provided!");
        }
        if (types.length == 1) {
            return new TypeList(Collections.singletonList(types[0]));
        }
        return new TypeList(Arrays.asList(types));
    }

    @Override
    public Iterator<Class<? extends Block>> iterator() {
        return types.iterator();
    }

    @Override
    public int compare(Block b1, Block b2) {
        return getIndex(b1) - getIndex(b2);
    }

    private int getIndex(Object o) {
        int max = -1;
        for (int i = 0; i < types.size(); i++) {
            Class<?> type = types.get(i);
            if (type.isInstance(o)) {
                max = Math.max(max, i);
            }
        }
        return max == -1 ? types.size() : max;
    }
}
