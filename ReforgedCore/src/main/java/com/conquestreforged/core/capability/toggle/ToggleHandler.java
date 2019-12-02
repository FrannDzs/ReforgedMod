package com.conquestreforged.core.capability.toggle;

import com.conquestreforged.core.capability.Capabilities;
import com.conquestreforged.core.networking.MessageHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ToggleHandler implements MessageHandler<Toggle> {

    @Override
    public Toggle decode(PacketBuffer buffer) {
        return new Toggle(buffer.readInt());
    }

    @Override
    public void encode(Toggle message, PacketBuffer buffer) {
        buffer.writeInt(message.getIndex());
    }

    @Override
    public void handle(Toggle message, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            if (context.get() == null || context.get().getSender() == null) {
                return;
            }

            ServerPlayerEntity player = context.get().getSender();
            if (player == null) {
                return;
            }

            player.getCapability(Capabilities.TOGGLE).ifPresent(toggle -> {
                // update the server-side player entity with the client's toggle state ?
                // where does this get written to disk ?
                toggle.setIndex(message.getIndex());
            });

            context.get().setPacketHandled(true);
        });
    }
}
