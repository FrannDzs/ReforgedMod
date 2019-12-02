package com.conquestreforged.core.capability;

import com.conquestreforged.core.capability.provider.Provider;
import com.conquestreforged.core.capability.provider.SimpleProvider;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class CapabilityRegister {

    private final Map<Class<?>, List<Supplier<? extends Provider<?>>>> providers = new HashMap<>();

    public synchronized <T, C extends Capability<T>> void registerSimple(Class<? extends ICapabilityProvider> holder, Class<T> type, Capability.IStorage<T> store, Supplier<C> provider) {
        register(holder, type, store, () -> new SimpleProvider<>(provider.get()));
    }

    public synchronized <T, C extends Capability<T>> void registerSimple(Class<? extends ICapabilityProvider> holder, Class<T> type, Callable<? extends T> factory, Capability.IStorage<T> store, Supplier<C> provider) {
        register(holder, type, factory, store, () -> new SimpleProvider<>(provider.get()));
    }

    /**
     * @param holder - the type of object that holds this capability (PlayerEntity, ItemStack, etc)
     * @param type - the type of the capability
     * @param store - the capability storage handler
     * @param provider - the provider that gets attached to the 'holder' in future events
     */
    public synchronized <T> void register(Class<? extends ICapabilityProvider> holder, Class<T> type, Capability.IStorage<T> store, Supplier<Provider<T>> provider) {
        register(holder, type, type::newInstance, store, provider);
    }

    /**
     * @param holder - the type of object that holds this capability (PlayerEntity, ItemStack, etc)
     * @param type - the type of the capability
     * @param factory - the factory that creates new instances of the type
     * @param store - the capability storage handler
     * @param provider - the provider that gets attached to the 'holder' in future events
     */
    public synchronized <T> void register(Class<? extends ICapabilityProvider> holder, Class<T> type, Callable<? extends T> factory, Capability.IStorage<T> store, Supplier<Provider<T>> provider) {
        CapabilityManager.INSTANCE.register(type, store, factory);
        providers.computeIfAbsent(type, c -> new LinkedList<>()).add(provider);
    }

    public synchronized <T extends ICapabilityProvider> List<Provider<?>> getCapabilities(T holder) {
        List<Provider<?>> list = new LinkedList<>();
        for (Map.Entry<Class<?>, List<Supplier<? extends Provider<?>>>> entry : providers.entrySet()) {
            if (entry.getKey().isInstance(holder)) {
                for (Supplier<? extends Provider<?>> supplier : entry.getValue()) {
                    list.add(supplier.get());
                }
            }
        }
        return list;
    }
}
