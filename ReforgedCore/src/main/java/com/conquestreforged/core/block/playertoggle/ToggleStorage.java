package com.conquestreforged.core.block.playertoggle;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

import javax.annotation.Nullable;

public class ToggleStorage implements IStorage<IToggle> {
    @Nullable
    @Override
    public INBT writeNBT(Capability<IToggle> capability, IToggle instance, Direction side) {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("toggle", instance.getToggle());
        return tag;
    }

    @Override
    public void readNBT(Capability<IToggle> capability, IToggle instance, Direction side, INBT nbt) {
        CompoundNBT tag = (CompoundNBT) nbt;
        instance.setToggle(tag.getInt("toggle"));
    }
}
