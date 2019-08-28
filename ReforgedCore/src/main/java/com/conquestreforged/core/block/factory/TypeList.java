package com.conquestreforged.core.block.factory;

import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;

import java.util.*;

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
        List<Class<? extends Block>> list = new ArrayList<>();
        Collections.addAll(list, types);
        return new TypeList(list);
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
            Class type = types.get(i);
            if (type.isInstance(o)) {
                max = Math.max(max, i);
            }
        }
        return max == -1 ? types.size() : max;
    }
}
