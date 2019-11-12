package com.conquestreforged.core.block.playertoggle;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ToggleProvider implements ICapabilitySerializable<INBT> {
    @CapabilityInject(IToggle.class)
    public static final Capability<IToggle> PLAYER_TOGGLE = null;

    private IToggle instance = PLAYER_TOGGLE.getDefaultInstance();

    private LazyOptional<IToggle> lazyop;

    public ToggleProvider() {
        lazyop = LazyOptional.of(() -> this.instance);
    }

    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return PLAYER_TOGGLE.orEmpty(cap,lazyop);
    }

    @Override
    public INBT serializeNBT() {
        return PLAYER_TOGGLE.getStorage().writeNBT(PLAYER_TOGGLE, this.instance, null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        PLAYER_TOGGLE.getStorage().readNBT(PLAYER_TOGGLE, this.instance, null, nbt);
    }

    public static boolean canAttachTo(ICapabilityProvider entity) {
        if (!(entity instanceof PlayerEntity)) {
            return false;
        }
        try {
            if (entity.getCapability(PLAYER_TOGGLE).isPresent()) {
                return false;
            }
        } catch (NullPointerException ex) {
            // Forge seems to be screwing up somewhere?
            return false;
        }
        return true;
    }

}
