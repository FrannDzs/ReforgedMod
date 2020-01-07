package com.conquestreforged.client.palette.palette;

import com.conquestreforged.client.palette.palette2.PaletteSlot;
import com.conquestreforged.client.palette.shape.Bounds;
import com.conquestreforged.client.palette.shape.FloatMath;
import com.conquestreforged.client.palette.shape.Point;
import com.conquestreforged.client.palette.shape.Polygon;
import com.conquestreforged.client.palette.util.Render;
import com.conquestreforged.core.item.family.Family;
import com.conquestreforged.core.item.family.FamilyRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class Palette {

    private static final ResourceLocation WHEEL = new ResourceLocation("conquest:textures/gui/wheel.png");
    private static final ResourceLocation MASK0 = new ResourceLocation("conquest:textures/gui/wheel_mask0.png");
    private static final ResourceLocation MASK1 = new ResourceLocation("conquest:textures/gui/wheel_mask1.png");

    static final int RADIUS = 75;
    private static final int EDGES = 6;
    private static final int PADDING = 40;

    private final Style centerStyle;
    private final Style radialStyle;
    private final Slot centerSlot;
    private final List<Slot> radialSlots;

    private int centerX;
    private int centerY;

    public Palette(ItemStack center, List<ItemStack> stacks) {
        int index = stacks.indexOf(center);
        int count = index == -1 ? stacks.size() : stacks.size() - 1;
        centerStyle = Style.center();
        radialStyle = Style.radial(count);
        radialSlots = new ArrayList<>(count);
        centerSlot = new Slot(center, centerStyle);
        for (ItemStack stack : stacks) {
            if (stack.isItemEqual(center)) {
                continue;
            }
            radialSlots.add(new Slot(stack, radialStyle));
        }
    }

    public void render(int mx, int my) {
        Render.cleanup();

        // render the hexagon texture
        renderBackgroundTexture();


        // get the closest slot to the mouse cursor
        Slot closest = getClosestSlot(mx, my);

        // render radial and center slots
        Render.beginItems();
        renderRadialSlots(mx, my, closest);
        centerSlot.render(mx, my);
        Render.endItems();

        // render the name of the item under the mouse
        if (closest.mouseOver(mx, my)) {
            int left = centerX;
            int top = centerY + (RADIUS + 44) - 15;
            String text = closest.getStack().getDisplayName().getFormattedText();
            Render.drawCenteredString(left, top, 0xFFFFFF, text);
        }
    }

    public boolean leftClick(int mx, int my) {
        Slot closest = getClosestSlot(mx, my);
        boolean state = closest.isSelected();
        each(slot -> slot.setSelected(false));
        if (closest.mouseOver(mx, my)) {
            closest.setSelected(!state);
            return true;
        }
        return false;
    }

    public boolean shiftLeftClick(int mx, int my) {
        Slot closest = getClosestSlot(mx, my);
        if (closest.mouseOver(mx, my)) {
            closest.setSelected(!closest.isSelected());
            return true;
        }
        return false;
    }

    public Slot getClosestSlot(int mx, int my) {
        Slot closest = centerSlot;
        int distance2 = centerSlot.getPos().distance2(mx, my);
        for (Slot slot : radialSlots) {
            int dist2 = slot.getPos().distance2(mx, my);
            if (dist2 < distance2) {
                closest = slot;
                distance2 = dist2;
            }
        }
        return closest;
    }

    public void setPos(int posX, int posY) {
        this.centerX = posX;
        this.centerY = posY;

        Polygon polygon = new Polygon(EDGES, RADIUS, PADDING, PADDING);
        polygon.init(centerX, centerY);

        float spacing = 360F / radialSlots.size();
        float halfSpacing = spacing / 2F;

        for (int i = 0; i < radialSlots.size(); i++) {
            float angle = FloatMath.clampAngle((i * spacing) - 90);
            Point pos = polygon.getPosition(angle);
            Bounds bounds = polygon.getBounds(angle, halfSpacing);
            Slot slot = radialSlots.get(i);
            slot.setPos(pos, bounds);
        }

        centerSlot.setPos(new Point(posX, posY), Bounds.NONE);
    }

    public void each(Consumer<Slot> consumer) {
        consumer.accept(centerSlot);
        radialSlots.forEach(consumer);
    }

    private void renderBackgroundTexture() {
        int rad = RADIUS + 44;
        int diam = (RADIUS + 44) * 2;
        int left = centerX - rad;
        int top = centerY - rad;
        Render.drawTexture(WHEEL, left, top, diam, diam, 0, 0, diam, diam);
    }

    private void renderRadialSlots(int mx, int my, Slot closest) {
        int index = getSlotIndex(closest);
        if (index == -1) {
            index = 0;
        }

        for (int i = 0, visited = 0; visited < radialSlots.size(); i++) {
            if (i == 0) {
                visited++;
                renderSlot(index, mx, my);
            } else {
                visited += 2;
                renderSlot(index - i, mx, my);
                renderSlot(index + i, mx, my);
            }
        }
    }

    private void renderSlot(int index, int mx, int my) {
        int slotIndex = wrapSlotIndex(index);
        Slot slot = radialSlots.get(slotIndex);
        slot.render(mx, my);
    }

    private int wrapSlotIndex(int index) {
        if (index < 0) {
            return radialSlots.size() + index;
        } else if (index >= radialSlots.size()) {
            return index - radialSlots.size();
        } else {
            return index;
        }
    }

    private int getSlotIndex(Slot slot) {
        return radialSlots.indexOf(slot);
    }

    public static Optional<Palette> create(ItemStack stack) {
        if (stack.isEmpty()) {
            return Optional.empty();
        }
        return create(stack.getItem());
    }

    public static Optional<Palette> create(Item item) {
        return create(Block.getBlockFromItem(item));
    }

    public static Optional<Palette> create(Block block) {
        if (block == Blocks.AIR) {
            return Optional.empty();
        }

        Family<Block> family = FamilyRegistry.BLOCKS.getFamily(block);
        if (family.isAbsent()) {
            return Optional.empty();
        }

        NonNullList<ItemStack> items = NonNullList.create();
        family.addAllItems(family.getGroup(), items);
        Palette palette = new Palette(new ItemStack(block), items);
        return Optional.of(palette);
    }
}
