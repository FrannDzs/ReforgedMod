package com.conquestreforged.core.capability.provider;

import com.conquestreforged.core.init.Context;
import net.minecraft.nbt.INBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;

public interface Provider<T> extends ICapabilitySerializable<INBT> {

    @Nonnull
    T getValue();

    String getName();

    default ResourceLocation getRegistryName() {
        return Context.getInstance().newResourceLocation(getName());
    }
}
