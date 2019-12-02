package com.conquestreforged.core.capability.provider;

import com.google.common.base.Preconditions;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.NonNullSupplier;

public interface Provider<T> extends ICapabilitySerializable<CompoundNBT> {

    ResourceLocation getRegistryName();

    static <T> NonNullSupplier<T> supply(Capability<T> capability) {
        return () -> {
            T t = capability.getDefaultInstance();
            Preconditions.checkNotNull(t);
            return t;
        };
    }
}
