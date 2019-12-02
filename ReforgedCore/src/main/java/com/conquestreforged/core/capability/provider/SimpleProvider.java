package com.conquestreforged.core.capability.provider;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class SimpleProvider<V> implements Provider<V> {

    private final V value;
    private final LazyOptional<V> optional;
    private final Capability<V> capability;

    public SimpleProvider(Capability<V> capability) {
        this.capability = capability;
        this.value = capability.getDefaultInstance();
        this.optional = LazyOptional.of(this::getValue);
    }

    @Nonnull
    @Override
    public V getValue() {
        return value;
    }

    @Override
    public LazyOptional<V> getOptional() {
        return optional;
    }

    @Override
    public Capability<V> getCapability() {
        return capability;
    }
}
