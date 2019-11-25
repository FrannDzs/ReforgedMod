package com.conquestreforged.core.capability.toggle;

public class Toggle {

    public static final String PROTOCOL_NAME = "main";
    public static final String PROTOCOL_VERSION = "1";

    private int toggle = 0;

    public int getIndex() {
        return toggle;
    }

    public int setIndex(int toggle) {
        return this.toggle = toggle;
    }
}
