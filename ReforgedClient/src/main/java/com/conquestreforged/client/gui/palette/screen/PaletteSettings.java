package com.conquestreforged.client.gui.palette.screen;

import com.conquestreforged.client.gui.render.Curve;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.StringTextComponent;

import java.awt.*;

public class PaletteSettings extends Screen {

    // controls how large the zoom effect goes
    public float zoomScale = 1.1F;

    // lower values == fewer neighbours zoom in
    public float zoomSpread = 1.0F;

    // controls the rate of zoom effect as mouse nears stack
    public Curve zoomCurve = Curve.SQUARE;

    // size of the highlight around hovered items
    public float highlightScale = 1.1F;

    // color around un-hovered items
    public int highlightColor = Color.BLACK.getRGB();

    // color of the highlight around hovered items
    public int hoveredColor = Color.GRAY.getRGB();

    // color of the highlight around selected/dragged items
    public int selectedColor = Color.WHITE.getRGB();

    private final Panel left = Panel.left(true);
    private final Panel right = Panel.right(true);

    public PaletteSettings() {
        super(new StringTextComponent("Settings"));
    }

    @Override
    public void init(Minecraft mc, int width, int height) {
        super.init(mc, width, height);
//
//        addButton(right, new ColorPicker("", highlightColor, c -> highlightColor = c));
//        addButton(right, new ColorPicker("", hoveredColor, c -> hoveredColor = c));
//        addButton(right, new ColorPicker("", selectedColor, c -> selectedColor = c));
//
//        left.setSize(this, width / 3, height);
//
//        right.setSize(this, width / 3, height);
    }

    @Override
    public void render(int mx, int my, float ticks) {
        left.tick();
        right.tick();
        super.render(mx, my, ticks);
    }

    private void addButton(Panel panel, Widget widget) {
        super.addButton(widget);
        panel.add(widget);
    }
}
