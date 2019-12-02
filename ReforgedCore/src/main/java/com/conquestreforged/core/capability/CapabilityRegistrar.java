package com.conquestreforged.core.capability;

import com.conquestreforged.core.capability.provider.SimpleProvider;
import com.conquestreforged.core.capability.provider.ProviderFactory;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class CapabilityRegistrar {

    private final Map<Class<?>, List<ProviderFactory<?>>> cache = new HashMap<>();
    private final Map<Class<?>, List<ProviderFactory<?>>> factories = new HashMap<>();

    public synchronized <T, C extends Capability<T>> void registerSimple(Class<? extends ICapabilityProvider> holder, Class<T> type, Capability.IStorage<T> store, Supplier<C> capability) {
        register(holder, type, store, () -> new SimpleProvider<>(capability.get()));
    }

    public synchronized <T, C extends Capability<T>> void registerSimple(Class<? extends ICapabilityProvider> holder, Class<T> type, Callable<? extends T> factory, Capability.IStorage<T> store, Supplier<C> capability) {
        register(holder, type, factory, store, () -> new SimpleProvider<>(capability.get()));
    }

    /**
     * @param holder - the type of object that holds this capability (PlayerEntity, ItemStack, etc)
     * @param type - the type of the capability
     * @param store - the capability storage handler
     * @param provider - the provider that gets attached to the 'holder' in future events
     */
    public synchronized <T> void register(Class<? extends ICapabilityProvider> holder, Class<T> type, Capability.IStorage<T> store, ProviderFactory<T> provider) {
        register(holder, type, type::newInstance, store, provider);
    }

    /**
     * @param holder - the type of object that holds this capability (PlayerEntity, ItemStack, etc)
     * @param type - the type of the capability
     * @param factory - the factory that creates new instances of the type
     * @param store - the capability storage handler
     * @param provider - the provider that gets attached to the 'holder' in future events
     */
    public synchronized <T> void register(Class<? extends ICapabilityProvider> holder, Class<T> type, Callable<? extends T> factory, Capability.IStorage<T> store, ProviderFactory<T> provider) {
        CapabilityManager.INSTANCE.register(type, store, factory);
        factories.computeIfAbsent(type, c -> new LinkedList<>()).add(provider);
    }

    public synchronized <T extends ICapabilityProvider> List<ProviderFactory<?>> getCapabilities(Class<T> holder) {
        return cache.computeIfAbsent(holder, this::compute);
    }

    private synchronized List<ProviderFactory<?>> compute(Class<?> type) {
        List<ProviderFactory<?>> list = new LinkedList<>();
        for (Map.Entry<Class<?>, List<ProviderFactory<?>>> entry : factories.entrySet()) {
            if (entry.getKey().isAssignableFrom(type)) {
                list.addAll(entry.getValue());
            }
        }
        return list;
    }
}
