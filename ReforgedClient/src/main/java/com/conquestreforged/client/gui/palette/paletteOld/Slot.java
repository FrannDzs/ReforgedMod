package com.conquestreforged.client.gui.palette.paletteOld;

import com.conquestreforged.client.gui.palette.screen.Style;
import com.conquestreforged.client.gui.palette.shape.Bounds;
import com.conquestreforged.client.gui.palette.shape.Point;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class Slot {

    private static final ResourceLocation SLOT = new ResourceLocation("conquest:textures/gui/slot.png");

    private final ItemStack stack;
    private final Style style;

    private Bounds bounds;
    private Point pos;

    private boolean selected = false;

    public Slot(ItemStack stack, Style style) {
        this.style = style;
        this.stack = stack;
        this.pos = Point.ZERO;
        this.bounds = Bounds.NONE;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setPos(Point pos, Bounds bounds) {
        this.pos = pos;
        this.bounds = bounds;
    }

    public boolean mouseOver(int x, int y) {
        if (isEmpty()) {
            return false;
        }
        return x >= pos.x - 11 && x <= pos.x + 11 && y >= pos.y - 11 && y <= pos.y + 11;
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public void render(int mx, int my) {
        if (!isEmpty()) {
            boolean hovered = mouseOver(mx, my);

            Render.beginSlot(pos.x, pos.y, hovered ? 10 : 0, getScale(mx, my));
            Render.drawTexture(SLOT, -11, -11, 22, 22, 0, 0, 22, 22);
            if (selected || hovered) {
                int color = selected ? style.selectedColor : style.hoveredColor;
                Render.drawItemStackHighlight(stack, -8, -8, 1.1F, color);
            }
            Render.drawItemStack(stack, -8, -8);
            Render.endSlot();
        }
    }

    public ItemStack getStack() {
        return stack;
    }

    public Bounds getBounds() {
        return bounds;
    }

    public Point getPos() {
        return pos;
    }

    public float getScale(int mx, int my) {
        if (!style.fixedScale) {
            float radius = Palette.RADIUS;
            float rad2 = radius * radius;
            float d2 = pos.distance2(mx, my);
            float alpha = (rad2 - d2) / rad2;
            float scale = style.scale;
            if (alpha > 0.3) {
                scale += Math.min(Math.max(alpha * alpha * alpha * 1.05F, 0), 2.5F);
            }
            return scale;
        }
        return style.scale;
    }
}
