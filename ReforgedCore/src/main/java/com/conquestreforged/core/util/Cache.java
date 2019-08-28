package com.conquestreforged.core.util;

import java.io.Closeable;
import java.util.*;

public abstract class Cache<K, V> implements Closeable {

    private static final List<Cache> cacheList = new LinkedList<>();

    private final Map<K, V> cache = new HashMap<>();

    protected Cache() {
        cacheList.add(this);
    }

    public final V get(K k) {
        return cache.computeIfAbsent(k, this::compute);
    }

    public void clear() {
        cache.clear();
    }

    @Override
    public void close() {
        clear();
    }

    public abstract V compute(K k);

    public static void clearAll() {
        Iterator<Cache> iterator = cacheList.iterator();
        while (iterator.hasNext()) {
            iterator.next().clear();
            iterator.remove();
        }
    }
}
