package com.conquestreforged.core.block.playertoggle;

public class PlayerToggle implements IToggle {
    private int toggle = 0;

    public PlayerToggle() {
    }

    @Override
    public int getToggle() {
        return toggle;
    }

    @Override
    public int setToggle(int toggle) {
        return this.toggle = toggle;
    }

}
