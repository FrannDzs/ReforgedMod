package com.conquestreforged.client.gui.palette.screen;

import com.conquestreforged.client.gui.render.Curve;

import java.awt.*;

public class PaletteSettings {

    // controls how large the zoom effect goes
    public float zoomScale = 1.1F;

    // lower values == fewer neighbours zoom in
    public float zoomSpread = 1.0F;

    // controls the rate of zoom effect as mouse nears stack
    public Curve zoomCurve = Curve.SQUARE;

    // size of the highlight around hovered items
    public float highlightScale = 1.1F;

    // color of the highlight around hovered items
    public int hoveredColor = Color.CYAN.getRGB();

    // color of the highlight around selected/dragged items
    public int selectedColor = Color.YELLOW.getRGB();
}
