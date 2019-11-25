package com.conquestreforged.core.networking;

import com.conquestreforged.core.capability.toggle.Toggle;
import com.conquestreforged.core.capability.toggle.ToggleHandler;
import com.conquestreforged.core.init.Context;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.Function;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Channels {

    public static final SimpleChannel TOGGLE = NetworkRegistry.newSimpleChannel(
            Context.getInstance().newResourceLocation(Toggle.PROTOCOL_NAME),
            () -> Toggle.PROTOCOL_VERSION,
            Toggle.PROTOCOL_VERSION::equals,
            Toggle.PROTOCOL_VERSION::equals
    );

    @SubscribeEvent
    public static void common(FMLCommonSetupEvent event) {
        Channels.register(Channels.TOGGLE, ToggleHandler.class, ToggleHandler::new);
    }

    private static <T extends PacketHandler> void register(SimpleChannel channel, Class<T> type, Function<PacketBuffer, T> factory) {
        channel.registerMessage(ChannelHelper.nextId(channel), type, PacketHandler::encode, factory, PacketHandler::handle);
    }
}
