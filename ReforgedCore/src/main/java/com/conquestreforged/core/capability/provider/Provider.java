package com.conquestreforged.core.capability.provider;

import com.google.common.base.Preconditions;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.NonNullSupplier;

public interface Provider<T> extends ICapabilityProvider {

    ResourceLocation getRegistryName();

    static <T> NonNullSupplier<T> supply(Capability<T> capability) {
        return () -> {
            T t = capability.getDefaultInstance();
            Preconditions.checkNotNull(t);
            return t;
        };
    }
}
