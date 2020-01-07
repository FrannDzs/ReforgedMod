package com.conquestreforged.client.palette.palette2;

import com.conquestreforged.client.palette.palette.Style;
import com.conquestreforged.client.palette.shape.Bounds;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class PaletteSlot extends Slot {

    private final Style style;
    private final Bounds bounds;

    public PaletteSlot(int index, int x, int y, Style style, IInventory inventory, Bounds bounds) {
        super(inventory, index, x - 8, y - 8);
        this.style = style;
        this.bounds = bounds;
    }

    @Override
    public ItemStack getStack() {
        return super.getStack().copy();
    }

    @Override
    public void putStack(ItemStack stack) {
//        super.putStack(stack);
    }

    @Override
    public ItemStack decrStackSize(int amount) {
        return getStack();
    }
}
