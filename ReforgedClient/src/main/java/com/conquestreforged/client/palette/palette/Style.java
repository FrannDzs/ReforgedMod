package com.conquestreforged.client.palette.palette;

public class Style {

    private static final float CENTER_SCALE = 1.75F;
    private static final float RADIAL_SCALE = 1.15F;
    private static final int SCALE_THRESHOLD = 18; // number of filled slots before scaling down items

    public final float scale;
    public final boolean fixedScale;

    public int selectedColor;
    public int hoveredColor;
    public float highlightScale;

    private Style(float scale, boolean fixedScale) {
        this.scale = scale;
        this.hoveredColor = 0xFFFFFF;
        this.selectedColor = 0x000000;
        this.fixedScale = fixedScale;
        this.highlightScale = scale + 0.01F;
    }

    public static Style center() {
        return new Style(CENTER_SCALE, true);
    }

    public static Style radial(int count){
        if (count > SCALE_THRESHOLD) {
            float dif = (count - SCALE_THRESHOLD) / (float) SCALE_THRESHOLD;
            float scale = RADIAL_SCALE - Math.min(dif, 0.45F);
            return new Style(scale, false);
        } else {
            return new Style(RADIAL_SCALE, false);
        }
    }
}
