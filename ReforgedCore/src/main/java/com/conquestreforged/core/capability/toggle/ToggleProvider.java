package com.conquestreforged.core.capability.toggle;

import com.conquestreforged.core.capability.provider.AbstractProvider;
import com.conquestreforged.core.capability.Capabilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ToggleProvider extends AbstractProvider<Toggle> {

    public ToggleProvider() {
        super(Capabilities.TOGGLE);
    }

    @Override
    public String getName() {
        return "toggle";
    }

    public static boolean canAttachTo(ICapabilityProvider entity) {
        if (!(entity instanceof PlayerEntity)) {
            return false;
        }
        try {
            if (entity.getCapability(Capabilities.TOGGLE).isPresent()) {
                return false;
            }
        } catch (NullPointerException ex) {
            // Forge seems to be screwing up somewhere?
            return false;
        }
        return true;
    }

}
