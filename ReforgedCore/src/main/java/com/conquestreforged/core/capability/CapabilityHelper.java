package com.conquestreforged.core.capability;

import com.conquestreforged.core.capability.handler.Handler;
import com.conquestreforged.core.capability.provider.ValueFactory;
import com.conquestreforged.core.capability.provider.SimpleValue;
import com.conquestreforged.core.networking.ChannelHelper;
import com.conquestreforged.core.networking.MessageHandler;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class CapabilityHelper {

    private static final Map<Class<?>, List<ValueFactory<?>>> cache = new HashMap<>();
    private static final Map<Class<?>, List<ValueFactory<?>>> factories = new HashMap<>();

    /**
     * Register the capability and associated networking handler
     *
     * @param capability - getter of the capability to register
     * @param channel - the channel used for client-server messaging
     * @param holder - the type that will hold the given capability
     * @param type - the capability value type
     * @param handler - the handler for encoding/decoding packets and reading/writing nbt
     */
    public static <T> void register(Supplier<Capability<T>> capability, SimpleChannel channel, Class<? extends ICapabilityProvider> holder, Class<T> type, Handler<T> handler) {
        register(channel, holder, type, handler, SimpleValue.factory(handler.getName(), capability));
    }

    /**
     * Register the capability and associated networking handler
     *
     * @param channel - the channel used for client-server messaging
     * @param holder - the type that will hold the given capability
     * @param type - the capability value type
     * @param handler - the handler for encoding/decoding packets and reading/writing nbt
     * @param factory - the capability provider factory
     */
    public static <T> void register(SimpleChannel channel, Class<? extends ICapabilityProvider> holder, Class<T> type, Handler<T> handler, ValueFactory<T> factory) {
        register(channel, holder, type, handler, handler, factory);
    }

    /**
     * Register the capability and associated networking handler
     *
     * @param channel - the channel used for client-server messaging
     * @param holder - the type that will hold the given capability
     * @param type - the capability value type
     * @param storage - handles reading/writing the value to nbt
     * @param handler - handles reading/writing the value to network packets
     * @param factory - the capability provider factory
     */
    public static <T> void register(SimpleChannel channel, Class<? extends ICapabilityProvider> holder, Class<T> type, Capability.IStorage<T> storage, MessageHandler<T> handler, ValueFactory<T> factory) {
        // register the capability
        CapabilityManager.INSTANCE.register(type, storage, type::newInstance);

        // create a channel message for networking
        channel.registerMessage(ChannelHelper.nextId(channel), type, handler::encode, handler::decode, handler::handle);

        // register the provider factories to the holder type
        factories.computeIfAbsent(holder, c -> new LinkedList<>()).add(factory);
    }

    /**
     * Get a list of Capability ProviderFactories to be added to the given holder type
     *
     * @param holder The type of capability holder (Entity, World, etc)
     * @return A list of ProviderFactories which can be used to create CapabilityProviders to be attached
     *          the given holder type
     */
    public static synchronized <T extends ICapabilityProvider> List<ValueFactory<?>> getCapabilities(Class<T> holder) {
        return cache.computeIfAbsent(holder, CapabilityHelper::compute);
    }

    private static synchronized List<ValueFactory<?>> compute(Class<?> type) {
        List<ValueFactory<?>> list = new LinkedList<>();
        for (Map.Entry<Class<?>, List<ValueFactory<?>>> entry : factories.entrySet()) {
            if (entry.getKey().isAssignableFrom(type)) {
                list.addAll(entry.getValue());
            }
        }
        return list;
    }
}
