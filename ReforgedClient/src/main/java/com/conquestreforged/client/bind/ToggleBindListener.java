package com.conquestreforged.client.bind;

import com.conquestreforged.client.gui.state.BlockStateScreen;
import com.conquestreforged.core.block.StateUtils;
import com.conquestreforged.core.client.input.BindEvent;
import com.conquestreforged.core.client.input.BindListener;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Optional;

public class ToggleBindListener implements BindListener {

    @Override
    public void onPress(BindEvent e) {
        if (!e.inGame || !e.player.isPresent()) {
            return;
        }

        e.player.map(PlayerEntity::getHeldItemMainhand).ifPresent(stack -> {
            Optional<BlockState> state = StateUtils.fromStack(stack);
            System.out.println(state);
            if (state.isPresent()) {
                BlockStateScreen screen = new BlockStateScreen(stack, state.get());
                Minecraft.getInstance().displayGuiScreen(screen);
                System.out.println(stack);
                return;
            }
        });
//
//        e.player.get().getCapability(Capabilities.TOGGLE).ifPresent(toggle -> {
//            // increment the toggle value 0-8
//            toggle.increment();
//
//            // send the new toggle state to server
//            Channels.TOGGLE.sendToServer(toggle);
//
//            // display client toggle state
//            Minecraft.getInstance().ingameGUI.setOverlayMessage(Integer.toString(toggle.getIndex()), false);
//        });
    }
}
