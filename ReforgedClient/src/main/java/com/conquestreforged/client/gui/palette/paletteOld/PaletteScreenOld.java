package com.conquestreforged.client.gui.palette.paletteOld;

import com.conquestreforged.client.gui.palette.shape.FloatMath;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;

public class PaletteScreenOld extends Screen {

    private final Palette palette;

    public PaletteScreenOld(Palette palette) {
        super(new StringTextComponent("Palette Screen"));
        this.palette = palette;
        super.minecraft = Minecraft.getInstance();
        super.font = Minecraft.getInstance().fontRenderer;
        super.itemRenderer = Minecraft.getInstance().getItemRenderer();
    }

    public void setup(int width, int height) {
        int centerX = width / 2;
        int centerY = height / 2;
        palette.setPos(centerX, centerY);
    }

    @Override
    public void init(Minecraft mc, int width, int height) {
        super.init(mc, width, height);
        setup(width, height);
    }

    @Override
    public void resize(Minecraft mc, int width, int height) {
        super.resize(mc, width, height);
        setup(width, height);
    }

    @Override
    public void render(int mx, int my, float ticks) {
        super.renderBackground();
        super.render(mx, my, ticks);
        palette.render(mx, my);
    }

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        int x = FloatMath.round(mx);
        int y = FloatMath.round(my);
        if (button == 0) {
            if (Screen.hasShiftDown()) {
                if (palette.shiftLeftClick(x, y)) {
                    return true;
                }
            } else {
                if (palette.leftClick(x, y)) {
                    return true;
                }
            }
        }
        return super.mouseClicked(mx, my, button);
    }
}
