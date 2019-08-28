package com.conquestreforged.core.proxy;

import net.minecraft.resources.ResourcePackType;
import net.minecraftforge.fml.LogicalSide;

public enum Side {
    CLIENT,
    SERVER,
    ;

    public Proxy getProxy() {
        return Proxies.get(this);
    }

    public static Side of(LogicalSide side) {
        return side == LogicalSide.CLIENT ? CLIENT : SERVER;
    }

    public static Side of(ResourcePackType type) {
        return type == ResourcePackType.CLIENT_RESOURCES ? CLIENT : SERVER;
    }
}
