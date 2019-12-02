package com.conquestreforged.core.capability.provider;

import com.conquestreforged.core.init.Context;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public interface Provider<T> extends ICapabilitySerializable<INBT> {

    T getValue();

    LazyOptional<T> getOptional();

    Capability<T> getCapability();

    @Nonnull
    @Override
    default <V> LazyOptional<V> getCapability(Capability<V> cap, Direction side) {
        return getCapability().orEmpty(cap, getOptional());
    }

    @Override
    default INBT serializeNBT() {
        return getCapability().getStorage().writeNBT(getCapability(), getValue(), null);
    }

    @Override
    default void deserializeNBT(INBT nbt) {
        getCapability().getStorage().readNBT(getCapability(), getValue(), null, nbt);
    }

    default ResourceLocation getRegistryName() {
        return Context.getInstance().newResourceLocation(getCapability().getName());
    }
}
