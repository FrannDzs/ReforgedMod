package com.conquestreforged.entities.painting.client;

import com.conquestreforged.entities.painting.art.Art;
import com.conquestreforged.entities.painting.client.gui.GuiPainting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientEvents {

    @SubscribeEvent
    public static void rightClick(PlayerInteractEvent.RightClickItem e) {
        if (e.getSide() != LogicalSide.CLIENT) {
            return;
        }

        ItemStack stack = e.getItemStack();
        String artName = "";

        CompoundNBT data = stack.getTag();
        if (data != null) {
            CompoundNBT painting = data.getCompound(Art.DATA_TAG);
            artName = painting.getString(Art.ART_TAG);
        }

        if (stack.getItem() == Items.PAINTING) {
            if (artName.isEmpty()) {
                artName = PaintingType.ALBAN.toString();
            }
            GuiPainting gui = new GuiPainting(stack, ForgeRegistries.PAINTING_TYPES.getValue(new ResourceLocation(artName)));
            Minecraft.getInstance().displayGuiScreen(gui);
        }
    }
}
