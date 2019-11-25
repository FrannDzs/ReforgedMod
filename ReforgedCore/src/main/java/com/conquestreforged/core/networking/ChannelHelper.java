package com.conquestreforged.core.networking;

import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ChannelHelper {

    private static final Map<SimpleChannel, AtomicInteger> messageIds = new HashMap<>();

    public static int nextId(SimpleChannel channel) {
        return messageIds.computeIfAbsent(channel, c -> new AtomicInteger(-1)).addAndGet(1);
    }
}
