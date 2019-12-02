package com.conquestreforged.core.capability.toggle;

import net.minecraftforge.common.capabilities.CapabilityProvider;

public class Toggle extends CapabilityProvider<Toggle> {

    public static final String PROTOCOL_NAME = "main";
    public static final String PROTOCOL_VERSION = "1";

    private int toggle = 0;

    public Toggle() {
        super(Toggle.class);
    }

    public int getIndex() {
        return toggle;
    }

    public int setIndex(int toggle) {
        return this.toggle = toggle;
    }

    public void increment() {
        if (toggle < 7) {
            toggle++;
        } else {
            toggle = 0;
        }
    }
}
