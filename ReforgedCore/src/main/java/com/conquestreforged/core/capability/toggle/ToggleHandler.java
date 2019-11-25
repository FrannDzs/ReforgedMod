package com.conquestreforged.core.capability.toggle;

import com.conquestreforged.core.capability.Capabilities;
import com.conquestreforged.core.networking.PacketHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ToggleHandler implements PacketHandler {

    public ToggleHandler() {

    }

    public ToggleHandler(PacketBuffer buf) {

    }

    @Override
    public void encode(PacketBuffer buf) {

    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            if (context.get() == null || context.get().getSender() == null) {
                return;
            }

            PlayerEntity player = context.get().getSender();
            if (player == null) {
                return;
            }

            Toggle toggle = player.getCapability(Capabilities.TOGGLE).orElseThrow(IllegalAccessError::new);
            int value = toggle.getIndex();
            if (value < 7) {
                toggle.setIndex(toggle.getIndex() + 1);
            } else {
                toggle.setIndex(0);
            }

            context.get().setPacketHandled(true);
        });
    }
}
