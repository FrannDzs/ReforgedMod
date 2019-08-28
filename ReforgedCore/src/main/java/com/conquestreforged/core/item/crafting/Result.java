package com.conquestreforged.core.item.crafting;

import com.conquestreforged.core.util.OptionalValue;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class Result implements OptionalValue {

    public static final Result EMPTY = new Result(Collections.emptyList(), Collections.emptyList());

    private final List<ItemStack> remaining;
    private final List<ItemStack> converted;

    public Result(List<ItemStack> remaining, List<ItemStack> converted) {
        this.remaining = remaining;
        this.converted = converted;
    }

    public List<ItemStack> getRemaining() {
        return remaining;
    }

    public List<ItemStack> getConverted() {
        return converted;
    }

    @Override
    public boolean isAbsent() {
        return this == EMPTY;
    }
}
