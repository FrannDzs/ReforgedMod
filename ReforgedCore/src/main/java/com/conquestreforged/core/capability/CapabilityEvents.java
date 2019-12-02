package com.conquestreforged.core.capability;

import com.conquestreforged.core.capability.provider.Provider;
import com.conquestreforged.core.capability.provider.ProviderFactory;
import com.conquestreforged.core.util.Log;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CapabilityEvents {

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        for (ProviderFactory<?> factory : CapabilityHelper.getCapabilities(event.getObject().getClass())) {
            Provider<?> provider = factory.create();
            event.addCapability(provider.getRegistryName(), provider);
            Log.debug("Adding capability: {} to: {}", provider.getRegistryName(), event.getObject());
        }
    }
}
