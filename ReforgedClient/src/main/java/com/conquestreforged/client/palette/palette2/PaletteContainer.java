package com.conquestreforged.client.palette.palette2;

import com.conquestreforged.client.palette.palette.Style;
import com.conquestreforged.client.palette.shape.Bounds;
import com.conquestreforged.client.palette.shape.FloatMath;
import com.conquestreforged.client.palette.shape.Point;
import com.conquestreforged.client.palette.shape.Polygon;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PaletteContainer extends Container {

    public static final ContainerType<PaletteContainer> TYPE = new ContainerType<>(PaletteContainer::new);

    public static final int RADIUS = 65;
    private static final int EDGES = 6;
    private static final int PADDING = 40;
    private static final int ANGLE_OFFSET = -90;

    private final Style centerStyle;
    private final Style radialStyle;
    private final IInventory inventory;
    private final PlayerInventory playerInventory;

    private PaletteContainer(int id, PlayerInventory inventory) {
        super(TYPE, id);
        this.inventory = inventory;
        this.playerInventory = inventory;
        this.centerStyle = Style.center();
        this.radialStyle = Style.radial(0);
    }

    public PaletteContainer(PlayerInventory inventory, ItemStack center, List<ItemStack> stacks) {
        super(TYPE, 0);
        this.playerInventory = inventory;
        this.inventory = toInventory(center, stacks);
        this.centerStyle = Style.center();
        this.radialStyle = Style.radial(getRadialSlotCount());
    }

    public void setPos(int centerX, int centerY) {
        super.inventorySlots.clear();

        // add the central slot first
        addSlot(new PaletteSlot(0, centerX, centerY, centerStyle, inventory, Bounds.NONE));

        Polygon polygon = new Polygon(EDGES, RADIUS, PADDING, PADDING);
        polygon.init(centerX, centerY);

        float spacing = 360F / getRadialSlotCount();
        float halfSpacing = spacing / 2F;
        for (int slotIndex = 1; slotIndex < inventory.getSizeInventory(); slotIndex++) {
            int posIndex = slotIndex - 1;
            float angle = FloatMath.clampAngle((posIndex * spacing) + ANGLE_OFFSET);
            Point pos = polygon.getPosition(angle);
            Bounds bounds = polygon.getBounds(angle, halfSpacing);
            // add each radial slot
            addSlot(new PaletteSlot(slotIndex, pos.x, pos.y, radialStyle, inventory, bounds));

            if (slotIndex >= inventory.getSizeInventory()) {
                throw new UnsupportedOperationException();
            }
        }

        int slotWidth = 18;
        int slotPadding = 8;
        int hotbarWidth = (9 * slotWidth) + slotPadding;
        int left = centerX -hotbarWidth / 2;
        int top = centerY + centerY - 10;
        for(int i = 0; i < 9; ++i) {
            int dx = (i * slotWidth) + slotPadding;
            this.addSlot(new Slot(playerInventory, i, left + dx - 8, top));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity player) {
        return true; //player.isCreative();
    }

    private int getRadialSlotCount() {
        return Math.max(0, inventory.getSizeInventory() - 1);
    }

    private static Inventory toInventory(ItemStack first, List<ItemStack> list) {
        List<ItemStack> result = new ArrayList<>(list.size());
        result.add(first);
        for (ItemStack stack : list) {
            if (!stack.isItemEqual(first)) {
                result.add(stack);
            }
        }
        return new Inventory(result.toArray(new ItemStack[0]));
    }
}