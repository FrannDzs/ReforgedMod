package com.conquestreforged.core.networking;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public interface PacketHandler {

    void encode(PacketBuffer buf);

    void handle(Supplier<NetworkEvent.Context> context);
}
