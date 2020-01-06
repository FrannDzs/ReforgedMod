package com.conquestreforged.client.toggle;

import com.conquestreforged.core.capability.Capabilities;
import com.conquestreforged.core.client.input.BindEvent;
import com.conquestreforged.core.client.input.BindListener;
import com.conquestreforged.core.networking.Channels;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.util.LazyOptional;

public class ToggleBindListener implements BindListener {
    @Override
    public void onPress(BindEvent e) {
        if (!e.inGame || !e.player.isPresent()) {
            return;
        }

        e.player.get().getCapability(Capabilities.TOGGLE).ifPresent(toggle -> {
            // increment the toggle value 0-8
            toggle.increment();

            // send the new toggle state to server
            Channels.TOGGLE.sendToServer(toggle);

            // display client toggle state
            Minecraft.getInstance().ingameGUI.setOverlayMessage(Integer.toString(toggle.getIndex()), false);
        });
    }
}
