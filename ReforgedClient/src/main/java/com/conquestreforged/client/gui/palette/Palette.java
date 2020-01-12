package com.conquestreforged.client.gui.palette;

import com.conquestreforged.core.item.family.Family;
import com.conquestreforged.core.item.family.FamilyRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Palette {

    public static IInventory createTestPalette(ItemStack stack) {
        List<ItemStack> stacks = Arrays.asList(
                stack,
                new ItemStack(Items.BIRCH_PLANKS),
                new ItemStack(Items.BIRCH_DOOR),
                new ItemStack(Items.BIRCH_FENCE),
                new ItemStack(Items.BIRCH_FENCE_GATE),
                new ItemStack(Items.BIRCH_SLAB),
                new ItemStack(Items.BIRCH_STAIRS)
        );
        return createPalette(stack, stacks);
    }

    public static IInventory createPalette(ItemStack first, List<ItemStack> family) {
        List<ItemStack> result = new ArrayList<>(family.size());
        result.add(copyOne(first));
        for (ItemStack stack : family) {
            if (!stack.isItemEqual(first)) {
                result.add(copyOne(stack));
            }
        }
        return new Inventory(result.toArray(new ItemStack[0]));
    }

    public static Optional<IInventory> getPalette(ItemStack stack) {
        return getPalette(stack, stack.getItem());
    }

    private static Optional<IInventory> getPalette(ItemStack stack, Item item) {
        if (item instanceof BlockItem) {
            return getPalette(stack, ((BlockItem) item).getBlock());
        }
        return Optional.empty();
    }

    private static Optional<IInventory> getPalette(ItemStack stack, Block block) {
        if (block == Blocks.AIR) {
            return Optional.empty();
        }

        Family<Block> family = FamilyRegistry.BLOCKS.getFamily(block);
        if (family.isAbsent()) {
            return Optional.empty();
        }

        NonNullList<ItemStack> items = NonNullList.create();
        family.addAllItems(family.getGroup(), items);

        return Optional.of(createPalette(stack, items));
    }

    private static ItemStack copyOne(ItemStack stack) {
        stack = stack.copy();
        stack.setCount(1);
        return stack;
    }
}
