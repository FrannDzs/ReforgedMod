package com.conquestreforged.core.capability.utils;

import com.conquestreforged.core.init.Context;
import net.minecraft.nbt.INBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public interface Provider<T> extends ICapabilitySerializable<INBT> {

    T getValue();

    String getName();

    default ResourceLocation getRegistryName() {
        return Context.getInstance().newResourceLocation(getName());
    }
}
