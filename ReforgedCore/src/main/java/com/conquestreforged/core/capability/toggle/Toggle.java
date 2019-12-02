package com.conquestreforged.core.capability.toggle;

import net.minecraftforge.common.capabilities.CapabilityProvider;

public class Toggle extends CapabilityProvider<Toggle> {

    public static final String PROTOCOL_NAME = "toggle";
    public static final String PROTOCOL_VERSION = "1";

    private int index = 0;

    public Toggle() {
        super(Toggle.class);
    }

    public Toggle(int index) {
        super(Toggle.class);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public int setIndex(int toggle) {
        return this.index = toggle;
    }

    public void increment() {
        if (index < 7) {
            index++;
        } else {
            index = 0;
        }
    }
}
