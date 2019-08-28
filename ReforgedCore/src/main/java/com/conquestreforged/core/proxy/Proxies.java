package com.conquestreforged.core.proxy;

import com.conquestreforged.core.proxy.impl.DefaultProxy;
import net.minecraft.resources.ResourcePackType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Proxies {

    private static final Map<Side, Proxy> proxies = new ConcurrentHashMap<>();

    public static void set(Side side, Proxy proxy) {
        proxies.put(side, proxy);
    }

    public static Proxy get(ResourcePackType type) {
        return get(Side.of(type));
    }

    public static Proxy get(Side side) {
        return proxies.getOrDefault(side, DefaultProxy.INSTANCE);
    }
}
