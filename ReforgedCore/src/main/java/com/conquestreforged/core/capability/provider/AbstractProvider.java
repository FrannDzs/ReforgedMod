package com.conquestreforged.core.capability.provider;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractProvider<V> implements Provider {

    private final V value;
    private final LazyOptional<V> optional;
    private final Capability<V> capability;

    public AbstractProvider(Capability<V> capability) {
        this.value = capability.getDefaultInstance();
        this.capability = capability;
        this.optional = LazyOptional.of(this::getValue);
    }

    @Nonnull
    @Override
    public V getValue() {
        return value;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return capability.orEmpty(cap, optional);
    }

    @Override
    public INBT serializeNBT() {
        return capability.getStorage().writeNBT(capability, this.value, null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        capability.getStorage().readNBT(capability, this.value, null, nbt);
    }
}
