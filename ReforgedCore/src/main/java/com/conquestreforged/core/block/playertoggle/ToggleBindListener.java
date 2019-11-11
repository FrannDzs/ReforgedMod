package com.conquestreforged.core.block.playertoggle;

import com.conquestreforged.core.client.input.BindEvent;
import com.conquestreforged.core.client.input.BindListener;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

public class ToggleBindListener implements BindListener {
    @Override
    public void onPress(BindEvent e) {
        if (!e.inGame || !e.player.isPresent()) {
            return;
        }
        Message.INSTANCE.sendToServer(new PacketSendKey());

        PlayerEntity playerEntity = e.player.get();
        IToggle cap = playerEntity.getCapability(ToggleProvider.PLAYER_TOGGLE).orElseThrow(IllegalAccessError::new);
        int togglenumber = cap.getToggle();
        Minecraft.getInstance().ingameGUI.setOverlayMessage(Integer.toString(togglenumber), false);
    }
}
