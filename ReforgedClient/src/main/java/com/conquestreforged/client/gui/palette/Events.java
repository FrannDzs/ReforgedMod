package com.conquestreforged.client.gui.palette;

import com.conquestreforged.client.BindManager;
import com.conquestreforged.client.gui.palette.screen.PaletteScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Events {

    @SubscribeEvent
    public static void onKeyPress(GuiScreenEvent.KeyboardKeyPressedEvent.Pre event) {
        System.out.println("Key: " + event.getKeyCode());

        if (event.getGui() instanceof ContainerScreen) {
            if (event.getKeyCode() != BindManager.getPaletteBind().getKey().getKeyCode()) {
                return;
            }

            PlayerEntity player = Minecraft.getInstance().player;
            if (player == null || !player.abilities.isCreativeMode) {
                return;
            }

            ContainerScreen<?> screen = (ContainerScreen<?>) event.getGui();
            Slot slot = screen.getSlotUnderMouse();
            if (slot == null || !slot.getHasStack()) {
                return;
            }

            ItemStack stack = slot.getStack();
            Optional<IInventory> palette = Palette.getPalette(stack);
            if (!palette.isPresent()) {
                return;
            }

            event.setCanceled(true);
            PaletteContainer container = new PaletteContainer(player.inventory, palette.get());
            PaletteScreen paletteScreen = new PaletteScreen(screen, player, player.inventory, container);
            Minecraft.getInstance().displayGuiScreen(paletteScreen);
        }
    }
}
