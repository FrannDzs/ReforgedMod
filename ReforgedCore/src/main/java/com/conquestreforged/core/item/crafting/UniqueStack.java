package com.conquestreforged.core.item.crafting;

import net.minecraft.item.ItemStack;

import java.util.Objects;

public class UniqueStack {

    private final ItemStack stack;

    public UniqueStack(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniqueStack that = (UniqueStack) o;
        return stack.getItem() == that.stack.getItem() && stack.getDamage() == that.stack.getDamage();
    }

    @Override
    public int hashCode() {
        return Objects.hash(stack.getItem(), stack.getDamage());
    }
}
