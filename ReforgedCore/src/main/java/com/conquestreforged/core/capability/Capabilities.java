package com.conquestreforged.core.capability;

import com.conquestreforged.core.capability.provider.Provider;
import com.conquestreforged.core.capability.toggle.Toggle;
import com.conquestreforged.core.capability.toggle.ToggleStorage;
import com.conquestreforged.core.util.Dummy;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.List;

/**
 * Holds all the player capabilities
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Capabilities {

    @CapabilityInject(Toggle.class)
    public static final Capability<Toggle> TOGGLE = Dummy.dummy();

    private static final CapabilityRegister registrar = new CapabilityRegister();

    @SubscribeEvent
    public static void common(FMLCommonSetupEvent event) {
        //  NOTE -  TOGGLE is null at time of this event as this is where it's created/registered!
        //          Therefore; must use the Supplier pattern `() -> TOGGLE` to access it in the future
        registrar.registerSimple(PlayerEntity.class, Toggle.class, new ToggleStorage(), () -> TOGGLE);
    }

    public static synchronized <T extends ICapabilityProvider> List<Provider<?>> getCapabilities(T holder) {
        return registrar.getCapabilities(holder);
    }
}
