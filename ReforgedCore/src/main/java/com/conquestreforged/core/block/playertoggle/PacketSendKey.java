package com.conquestreforged.core.block.playertoggle;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSendKey {
    public PacketSendKey() {

    }

    public PacketSendKey(PacketBuffer buf) {

    }

    public void encode(PacketBuffer buf) {

    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            if (context.get() == null || context.get().getSender() == null) {
                return;
            }
            PlayerEntity playerEntity = context.get().getSender();
            IToggle cap = playerEntity.getCapability(ToggleProvider.PLAYER_TOGGLE).orElseThrow(IllegalAccessError::new);
            int togglenumber = cap.getToggle();
            if (togglenumber < 7) {
                cap.setToggle(togglenumber + 1);
            } else {
                cap.setToggle(0);
            }
            context.get().setPacketHandled(true);
        });
    }
}
