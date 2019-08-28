package com.conquestreforged.core.util;

import java.util.List;

public class ListUtils {

    public static int wrapIndex(int size, int index) {
        while (index < 0) {
            index = size + index;
        }
        while (index >= size) {
            index = index - size;
        }
        return index;
    }

    public static <T> T getWrapped(List<T> list, int index) {
        return list.get(wrapIndex(list.size(), index));
    }
}
