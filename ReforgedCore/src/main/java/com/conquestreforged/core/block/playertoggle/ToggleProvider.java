package com.conquestreforged.core.block.playertoggle;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ToggleProvider implements ICapabilitySerializable<INBT> {
    @CapabilityInject(IToggle.class)
    public static final Capability<IToggle> PLAYER_TOGGLE = null;

    private LazyOptional<IToggle> instance = LazyOptional.of(PLAYER_TOGGLE::getDefaultInstance);

    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == PLAYER_TOGGLE ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return PLAYER_TOGGLE.getStorage().writeNBT(PLAYER_TOGGLE, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty")), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        PLAYER_TOGGLE.getStorage().readNBT(PLAYER_TOGGLE, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty")), null, nbt);
    }

}
