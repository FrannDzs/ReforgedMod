package com.conquestreforged.core.capability.toggle;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

import javax.annotation.Nullable;

public class ToggleStorage implements IStorage<Toggle> {
    @Nullable
    @Override
    public INBT writeNBT(Capability<Toggle> capability, Toggle instance, Direction side) {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("toggle", instance.getIndex());
        return tag;
    }

    @Override
    public void readNBT(Capability<Toggle> capability, Toggle instance, Direction side, INBT nbt) {
        CompoundNBT tag = (CompoundNBT) nbt;
        instance.setIndex(tag.getInt("toggle"));
    }
}
