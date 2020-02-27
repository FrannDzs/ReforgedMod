package com.conquestreforged.core.item.group.sort;

import java.util.List;

public interface Sorter<T> {

    Sorter NONE = l -> {};

    void apply(List<T> list);

    @SuppressWarnings("unchecked")
    static <T> Sorter<T> none() {
        return (Sorter<T>) NONE;
    }
}
