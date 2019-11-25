package com.conquestreforged.client.toggle;

import com.conquestreforged.core.capability.Caps;
import com.conquestreforged.core.capability.Capabilities;
import com.conquestreforged.core.capability.toggle.Toggle;
import com.conquestreforged.core.capability.toggle.ToggleHandler;
import com.conquestreforged.core.client.input.BindEvent;
import com.conquestreforged.core.client.input.BindListener;
import com.conquestreforged.core.networking.Channels;
import net.minecraft.client.Minecraft;

public class ToggleBindListener implements BindListener {
    @Override
    public void onPress(BindEvent e) {
        if (!e.inGame || !e.player.isPresent()) {
            return;
        }

        Channels.TOGGLE.sendToServer(new ToggleHandler());
        int index = Caps.get(e.player.get(), Capabilities.TOGGLE, Toggle::getIndex, 0);
        Minecraft.getInstance().ingameGUI.setOverlayMessage(Integer.toString(index), false);
    }
}
