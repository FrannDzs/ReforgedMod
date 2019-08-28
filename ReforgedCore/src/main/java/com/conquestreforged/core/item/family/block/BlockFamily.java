package com.conquestreforged.core.item.family.block;

import com.conquestreforged.core.block.factory.TypeList;
import com.conquestreforged.core.item.family.Family;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;
import java.util.Collections;

public class BlockFamily extends Family<Block> {

    public static final BlockFamily EMPTY = new BlockFamily();

    private BlockFamily() {
        super(ItemGroup.SEARCH, (b1, b2) -> 0, Collections.emptyList());
    }

    public BlockFamily(ItemGroup group, TypeList order) {
        super(group, order, new ArrayList<>());
    }

    @Override
    protected Block emptyValue() {
        return Blocks.AIR;
    }

    @Override
    protected void addItem(ItemGroup group, NonNullList<ItemStack> list, Block block) {
        block.fillItemGroup(group, list);
    }

    @Override
    public boolean isAbsent() {
        return this == EMPTY;
    }
}
