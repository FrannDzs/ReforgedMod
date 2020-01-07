package com.conquestreforged.client.gui.palette;

import com.conquestreforged.client.gui.base.AbstractContainer;
import com.conquestreforged.client.gui.base.Hotbar;
import com.conquestreforged.client.gui.palette.screen.PaletteSlot;
import com.conquestreforged.client.gui.palette.screen.Style;
import com.conquestreforged.client.gui.palette.shape.Bounds;
import com.conquestreforged.client.gui.palette.shape.FloatMath;
import com.conquestreforged.client.gui.palette.shape.Point;
import com.conquestreforged.client.gui.palette.shape.Polygon;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class PaletteContainer extends AbstractContainer {

    public static final ContainerType<PaletteContainer> TYPE = new ContainerType<>(PaletteContainer::new);

    public static final int RADIUS = 65;
    private static final int EDGES = 6;
    private static final int PADDING = 40;
    private static final int ANGLE_OFFSET = -90;

    private final Style centerStyle;
    private final Style radialStyle;
    private final IInventory paletteInventory;
    private final PlayerInventory playerInventory;

    private final Hotbar hotbar;

    private PaletteContainer(int id, PlayerInventory inventory) {
        super(TYPE, id);
        this.hotbar = new Hotbar(inventory);
        this.paletteInventory = inventory;
        this.playerInventory = inventory;
        this.centerStyle = Style.center();
        this.radialStyle = Style.radial(0);
    }

    public PaletteContainer(PlayerInventory inventory, IInventory palette) {
        super(TYPE, 0);
        this.hotbar = new Hotbar(inventory);
        this.playerInventory = inventory;
        this.paletteInventory = palette;
        this.centerStyle = Style.center();
        this.radialStyle = Style.radial(getRadialSlotCount());
    }

    public void init(ContainerScreen<?> screen) {
        int centerX = screen.getXSize() / 2;
        int centerY = screen.getYSize() / 2;

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

        // screens art translated to guiTop so need to calc the bottom relative to y=guiTop rather than y=0
        int screenBottom = screen.height - screen.getGuiTop();
        int top = screenBottom - hotbar.getHeight();
        hotbar.addTo(this, centerX, top);
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

    public Hotbar getHotbar() {
        return hotbar;
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