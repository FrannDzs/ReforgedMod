package com.conquestreforged.core.capability;

import com.conquestreforged.core.capability.toggle.Toggle;
import com.conquestreforged.core.capability.toggle.ToggleProvider;
import com.conquestreforged.core.capability.toggle.ToggleStorage;
import com.conquestreforged.core.capability.provider.Provider;
import com.conquestreforged.core.util.Dummy;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * Holds all the player capabilities
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Capabilities {

    @CapabilityInject(Toggle.class)
    public static final Capability<Toggle> TOGGLE = Dummy.dummy();

    private static final Map<Class<?>, List<Supplier<Provider>>> providers = new HashMap<>();

    @SubscribeEvent
    public static void common(FMLCommonSetupEvent event) {
        register(PlayerEntity.class, Toggle.class, new ToggleStorage(), ToggleProvider::new);
    }

    /**
     * @param holder - the type of object that holds this capability (PlayerEntity, ItemStack, etc)
     * @param type - the type of the capability
     * @param store - the capability storage handler
     * @param provider - the capability provider
     */
    private static synchronized <T> void register(Class<? extends ICapabilityProvider> holder, Class<T> type, Capability.IStorage<T> store, Supplier<Provider> provider) {
        register(holder, type, type::newInstance, store, provider);
    }

    /**
     * @param holder - the type of object that holds this capability (PlayerEntity, ItemStack, etc)
     * @param type - the type of the capability
     * @param store - the capability storage handler
     * @param provider - the capability provider
     */
    private static synchronized <T> void register(Class<? extends ICapabilityProvider> holder, Class<T> type, Callable<? extends T> factory, Capability.IStorage<T> store, Supplier<Provider> provider) {
        CapabilityManager.INSTANCE.register(type, store, factory);
        Capabilities.providers.computeIfAbsent(holder, c -> new LinkedList<>()).add(provider);
    }

    static synchronized <T extends ICapabilityProvider> List<Provider> getCapabilities(T holder) {
        List<Provider> list = new LinkedList<>();
        for (Map.Entry<Class<?>, List<Supplier<Provider>>> entry : providers.entrySet()) {
            if (entry.getKey().isInstance(holder)) {
                for (Supplier<Provider> supplier : entry.getValue()) {
                    list.add(supplier.get());
                }
            }
        }
        return list;
    }
}
