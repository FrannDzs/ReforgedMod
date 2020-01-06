package com.conquestreforged.client.palette;

import com.conquestreforged.client.palette.palette.Palette;
import com.conquestreforged.client.palette.palette.PaletteScreen;
import com.conquestreforged.core.client.input.BindEvent;
import com.conquestreforged.core.client.input.BindListener;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

public class PaletteBindListener implements BindListener {

    @Override
    public void onPress(BindEvent e) {
        if (!e.inGame || !e.player.isPresent()) {
            return;
        }

        e.player.map(PlayerEntity::getHeldItemMainhand).flatMap(Palette::create).ifPresent(palette -> {
            PaletteScreen screen = new PaletteScreen(palette);
            Minecraft.getInstance().displayGuiScreen(screen);
        });
    }
}
