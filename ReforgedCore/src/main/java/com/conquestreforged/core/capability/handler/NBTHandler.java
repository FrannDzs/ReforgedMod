package com.conquestreforged.core.capability.handler;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability;

public interface NBTHandler<T> extends Handler<T> {

    T create();

    Capability<T> getCapability();

    @Override
    default T decode(PacketBuffer buffer) {
        T value = create();
        CompoundNBT root = buffer.readCompoundTag();
        readNBT(getCapability(), value, null, root);
        return value;
    }

    @Override
    default void encode(T message, PacketBuffer buffer) {
        CompoundNBT root = new CompoundNBT();
        writeNBT(getCapability(), message, null);
        buffer.writeCompoundTag(root);
    }
}
