package com.conquestreforged.client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.inventory.CreativeCraftingListener;
import net.minecraft.item.ItemStack;

public class CreativeUtils {

    public static boolean replaceItemStack(ItemStack original, ItemStack stack) {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if (player == null || !player.abilities.instabuild) {
            return false;
        }

        int slot = player.inventory.findSlotMatchingItem(original);
        CreativeCraftingListener listener = new CreativeCraftingListener(Minecraft.getInstance());
        player.inventoryMenu.addSlotListener(listener);
        player.inventory.setItem(slot, stack);
        player.inventoryMenu.broadcastChanges();
        player.inventoryMenu.removeSlotListener(listener);
        return true;
    }

    public static boolean addItemStack(ItemStack stack) {
        return addItemStack(stack, false);
    }

    public static boolean addItemStack(ItemStack stack, boolean pick) {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if (player == null || !player.abilities.instabuild) {
            return false;
        }

        int slot = player.inventory.getSuitableHotbarSlot();
        CreativeCraftingListener listener = new CreativeCraftingListener(Minecraft.getInstance());
        player.inventoryMenu.addSlotListener(listener);
        player.inventory.setItem(slot, stack);
        player.inventoryMenu.broadcastChanges();
        player.inventoryMenu.removeSlotListener(listener);
        if (pick) {
            player.inventory.selected = slot;
        }
        return true;
    }
}
