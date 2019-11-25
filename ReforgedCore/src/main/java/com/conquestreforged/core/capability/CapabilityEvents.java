package com.conquestreforged.core.capability;

import com.conquestreforged.core.capability.utils.Provider;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CapabilityEvents {

    @SubscribeEvent
    public static void onAttachCapabilitiesEvent(AttachCapabilitiesEvent<Entity> event) {
        for (Provider provider : Capabilities.getCapabilities(event.getObject())) {
            event.addCapability(provider.getRegistryName(), provider);
        }
    }
}
