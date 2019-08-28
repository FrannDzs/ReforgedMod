package com.conquestreforged.entities.painting;

import com.conquestreforged.entities.ModItems;
import com.conquestreforged.entities.painting.art.Art;
import com.conquestreforged.entities.painting.item.PaintingItem;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.event.EventNetworkChannel;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class NetworkHandler {

    public static final Supplier<String> VERSION = () -> "1.0";
    public static final Predicate<String> CLIENT = s -> true;
    public static final Predicate<String> SERVER = s -> true;
    public static final ResourceLocation SYNC_CHANNEL = new ResourceLocation("conquest:painting-sync");

    private final EventNetworkChannel channel;

    public NetworkHandler() {
        channel = NetworkRegistry.newEventChannel(SYNC_CHANNEL, VERSION, CLIENT, SERVER);
        channel.registerObject(this);
    }

    @SubscribeEvent
    public void syncEvent(NetworkEvent.ServerCustomPayloadEvent e) {
        ServerPlayerEntity player = e.getSource().get().getSender();
        if (player == null) {
            return;
        }

        ItemStack stack = player.getHeldItemMainhand();
        if (stack.getItem() != ModItems.conquestPainting && stack.getItem() != ModItems.vanillaPainting && stack.getItem() != Items.PAINTING) {
            return;
        }

        PacketBuffer buffer = e.getPayload();
        CompoundNBT tag = buffer.readCompoundTag();
        if (tag == null) {
            return;
        }

        String type = tag.getString(Art.TYPE_TAG);
        String art = tag.getString(Art.ART_TAG);
        if (type.isEmpty() || art.isEmpty()) {
            return;
        }

        ItemStack newStack = PaintingItem.createStack(type, art);
        player.setHeldItem(Hand.MAIN_HAND, newStack);
        player.updateHeldItem();
    }
}
