package com.conquestreforged.core.client.input;

import net.minecraft.client.settings.KeyBinding;

import java.util.LinkedList;
import java.util.List;

public class EventBinding extends KeyBinding {

    private final List<BindListener> listeners = new LinkedList<>();

    private boolean down = false;

    public EventBinding(String description, int keyCode, String category) {
        super(description, keyCode, category);
    }

    public EventBinding addListener(BindListener listener) {
        listeners.add(listener);
        return this;
    }

    public boolean checkPressed() {
        boolean pressed = super.isPressed();
        if (pressed) {
            BindEvent event = new BindEvent(this);
            listeners.forEach(l -> l.onPress(event));
        }
        return pressed;
    }

    public boolean checkHeld() {
        boolean down = super.isKeyDown();
        if (down) {
            BindEvent event = new BindEvent(this);
            listeners.forEach(l -> l.onHold(event));
        } else if (this.down) {
            BindEvent event = new BindEvent(this);
            listeners.forEach(l -> l.onRelease(event));
        }
        return this.down = down;
    }
}
