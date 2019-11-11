package com.conquestreforged.core.block.playertoggle;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class Message {
    private static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel INSTANCE;

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.newSimpleChannel
                (new ResourceLocation("conquest", "main")
                        , () -> PROTOCOL_VERSION
                        , PROTOCOL_VERSION::equals
                        , PROTOCOL_VERSION::equals);
        int id = 0;
        INSTANCE.registerMessage(id++, PacketSendKey.class, PacketSendKey::encode, PacketSendKey::new, PacketSendKey::handle);
    }
}
