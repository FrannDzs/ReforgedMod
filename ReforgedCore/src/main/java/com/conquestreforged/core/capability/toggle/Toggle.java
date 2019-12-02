package com.conquestreforged.core.capability.toggle;

public class Toggle {

    public static final String PROTOCOL_NAME = "main";
    public static final String PROTOCOL_VERSION = "1";

    private int toggle = 0;

    public Toggle() {

    }

    public Toggle(int index) {
        this.toggle = index;
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
