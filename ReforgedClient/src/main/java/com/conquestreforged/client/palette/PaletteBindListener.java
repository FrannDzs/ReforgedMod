package com.conquestreforged.client.palette;

import com.conquestreforged.client.palette.palette.Palette;
import com.conquestreforged.client.palette.palette.PaletteScreen;
import com.conquestreforged.core.client.input.BindEvent;
import com.conquestreforged.core.client.input.BindListener;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Arrays;

public class PaletteBindListener implements BindListener {

    @Override
    public void onPress(BindEvent e) {
        if (!e.inGame || !e.player.isPresent()) {
            return;
        }

        PlayerEntity player = e.player.get();
        ItemStack held = player.getHeldItemMainhand();
        Palette palette = Palette.create(held).orElse(createDebugPalette(held));
        Minecraft.getInstance().displayGuiScreen(new PaletteScreen(palette));
    }

    private static Palette createDebugPalette(ItemStack stack) {
        return new Palette(stack, Arrays.asList(
                new ItemStack(Items.OAK_PLANKS),
                new ItemStack(Items.OAK_SLAB),
                new ItemStack(Items.OAK_STAIRS),
                new ItemStack(Items.OAK_DOOR),
                new ItemStack(Items.OAK_FENCE),
                new ItemStack(Items.OAK_FENCE_GATE),
                new ItemStack(Items.OAK_BOAT),
                new ItemStack(Items.OAK_BUTTON),
                new ItemStack(Items.OAK_TRAPDOOR),
                new ItemStack(Items.OAK_SIGN)
        ));
    }
}
