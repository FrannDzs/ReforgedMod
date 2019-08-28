package com.conquestreforged.client.search;

import com.conquestreforged.core.client.input.BindEvent;
import com.conquestreforged.core.client.input.BindListener;

public class SearchBindListener implements BindListener {
    @Override
    public void onPress(BindEvent e) {
        if (!e.inGame) {
            return;
        }
    }
}
