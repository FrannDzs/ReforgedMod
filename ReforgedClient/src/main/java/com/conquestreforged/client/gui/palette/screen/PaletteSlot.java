package com.conquestreforged.client.gui.palette.screen;

import com.conquestreforged.client.gui.palette.shape.Bounds;
import com.mojang.datafixers.util.Pair;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class PaletteSlot extends Slot {

    private final Style style;
    private final Bounds bounds;

    public PaletteSlot(IInventory inventory, Style style, Bounds bounds, int index, int x, int y) {
        super(inventory, index, x - 8, y - 8);
        this.style = style;
        this.bounds = bounds;
    }

    @Override
    public Pair<ResourceLocation, ResourceLocation> func_225517_c_() {
        return null;
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
