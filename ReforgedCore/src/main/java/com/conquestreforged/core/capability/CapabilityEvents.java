package com.conquestreforged.core.capability;

import com.conquestreforged.core.capability.provider.Value;
import com.conquestreforged.core.capability.provider.ValueFactory;
import com.conquestreforged.core.util.Log;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CapabilityEvents {

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        for (ValueFactory<?> factory : CapabilityHelper.getCapabilities(event.getObject().getClass())) {
            Value<?> value = factory.create();
            event.addCapability(value.getRegistryName(), value);
            Log.debug("Adding capability: '{}' to: {}", value.getRegistryName(), event.getObject().getType());
        }
    }
}
