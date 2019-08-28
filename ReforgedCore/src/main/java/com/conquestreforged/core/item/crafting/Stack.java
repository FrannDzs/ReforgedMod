package com.conquestreforged.core.item.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

import java.util.List;
import java.util.Objects;

public class Stack {

    private final ItemStack stack;
    private int count = 0;

    public Stack(ItemStack stack) {
        this.stack = stack;
    }

    public void inc(int amount) {
        count += amount;
    }

    public void mult(float value) {
        count = MathHelper.ceil(count * value);
    }

    public void drain(List<ItemStack> list) {
        int count = this.count;
        while (count > 0) {
            int size = Math.min(count, stack.getMaxStackSize());
            ItemStack copy = stack.copy();
            copy.setCount(size);
            list.add(copy);
            count -= size;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || other.getClass() != this.getClass()) {
            return false;
        }
        Stack that = (Stack) other;
        return this.stack.getItem() == that.stack.getItem() && this.stack.getDamage() == that.stack.getDamage();
    }

    @Override
    public int hashCode() {
        return Objects.hash(stack.getItem(), stack.getDamage());
    }
}
