package com.conquestreforged.core.capability.provider;

import com.conquestreforged.core.init.Context;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SimpleProvider<V> implements Provider<V> {

    private final Capability<V> capability;
    private final LazyOptional<V> optional;

    public SimpleProvider(Capability<V> capability) {
        this.capability = capability;
        this.optional = LazyOptional.of(Provider.supply(capability));
    }

    @Override
    public ResourceLocation getRegistryName() {
        return Context.getInstance().newResourceLocation(capability.getName());
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return capability.orEmpty(cap, optional);
    }
}
