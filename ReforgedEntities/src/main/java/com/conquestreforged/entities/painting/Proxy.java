package com.conquestreforged.entities.painting;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

import java.util.concurrent.atomic.AtomicReference;

public interface Proxy {

    AtomicReference<Proxy> instance = new AtomicReference<>(new CommonProxy());

    default void handlePaintingUse(ItemStack stack, String name, String artName) {}

    default void sendSyncPacket(PacketBuffer buffer) {}

    static Proxy get() {
        return instance.get();
    };
}
