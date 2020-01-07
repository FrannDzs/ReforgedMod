package com.conquestreforged.client.gui.palette;

import com.conquestreforged.client.gui.palette.screen.PaletteSlot;
import com.conquestreforged.client.gui.palette.screen.Style;
import com.conquestreforged.client.gui.palette.shape.Bounds;
import com.conquestreforged.client.gui.palette.shape.FloatMath;
import com.conquestreforged.client.gui.palette.shape.Point;
import com.conquestreforged.client.gui.palette.shape.Polygon;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class PaletteContainer extends Container {

    public static final ContainerType<PaletteContainer> TYPE = new ContainerType<>(PaletteContainer::new);

    public static final int RADIUS = 65;
    private static final int EDGES = 6;
    private static final int PADDING = 40;
    private static final int ANGLE_OFFSET = -90;

    private final Style centerStyle;
    private final Style radialStyle;
    private final IInventory paletteInventory;
    private final PlayerInventory playerInventory;

    private PaletteContainer(int id, PlayerInventory inventory) {
        super(TYPE, id);
        this.paletteInventory = inventory;
        this.playerInventory = inventory;
        this.centerStyle = Style.center();
        this.radialStyle = Style.radial(0);
    }

    public PaletteContainer(PlayerInventory inventory, IInventory palette) {
        super(TYPE, 0);
        this.playerInventory = inventory;
        this.paletteInventory = palette;
        this.centerStyle = Style.center();
        this.radialStyle = Style.radial(getRadialSlotCount());
    }

    public void setPos(int centerX, int centerY) {
        inventorySlots.clear();

        // add the central slot first
        addSlot(new PaletteSlot(paletteInventory, centerStyle, Bounds.NONE, 0, centerX, centerY));

        Polygon polygon = new Polygon(EDGES, RADIUS, PADDING, PADDING);
        polygon.init(centerX, centerY);

        float spacing = 360F / getRadialSlotCount();
        float halfSpacing = spacing / 2F;
        for (int slotIndex = 1; slotIndex < paletteInventory.getSizeInventory(); slotIndex++) {
            int posIndex = slotIndex - 1;
            float angle = FloatMath.clampAngle((posIndex * spacing) + ANGLE_OFFSET);
            Point pos = polygon.getPosition(angle);
            Bounds bounds = polygon.getBounds(angle, halfSpacing);
            // add each radial slot
            addSlot(new PaletteSlot(paletteInventory, radialStyle, bounds, slotIndex, pos.x, pos.y));

            if (slotIndex >= paletteInventory.getSizeInventory()) {
                throw new UnsupportedOperationException();
            }
        }

        int slotWidth = 20;
        int hotbarWidth = (9 * slotWidth);
        int left = centerX - (hotbarWidth / 2) + 2;
        int top = centerY + centerY - 7;
        for(int i = 0; i < 9; ++i) {
            int dx = i * slotWidth;
            this.addSlot(new Slot(playerInventory, i, left + dx, top));
        }
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
        if (index >= inventorySlots.size() - 9 && index < inventorySlots.size()) {
            Slot slot = inventorySlots.get(index);
            if (slot != null && slot.getHasStack()) {
                slot.putStack(ItemStack.EMPTY);
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canDragIntoSlot(Slot slot) {
        return slot.inventory != paletteInventory;
    }

    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slot) {
        return slot.inventory != paletteInventory;
    }

    @Override
    public boolean canInteractWith(PlayerEntity player) {
        return player.isCreative();
    }

    public IInventory getPaletteInventory() {
        return paletteInventory;
    }

    private int getRadialSlotCount() {
        return Math.max(0, paletteInventory.getSizeInventory() - 1);
    }

    private static ItemStack copyOne(ItemStack stack) {
        stack = stack.copy();
        stack.setCount(1);
        return stack;
    }
}