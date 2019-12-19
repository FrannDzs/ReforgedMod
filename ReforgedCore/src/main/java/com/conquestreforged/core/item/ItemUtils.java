package com.conquestreforged.core.item;

import com.conquestreforged.core.item.family.Family;
import com.conquestreforged.core.item.family.FamilyRegistry;
import com.conquestreforged.core.item.family.TypeFilter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.IProperty;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.StringTextComponent;

import java.util.Map;
import java.util.Optional;

public class ItemUtils {

    public static final String BLOCK_STATE_TAG = "BlockStateTag";

    public static ItemStack fromState(BlockState state) {
        ItemStack stack = new ItemStack(state.getBlock());
        CompoundNBT data = stack.getOrCreateChildTag(BLOCK_STATE_TAG);
        for (Map.Entry<IProperty<?>, ?> e : state.getValues().entrySet()) {
            data.putString(e.getKey().getName(), e.getValue().toString());
        }
        return stack;
    }

    public static ItemStack fromState(BlockState state, IProperty<?> property) {
        String value = state.get(property).toString();
        ItemStack stack = new ItemStack(state.getBlock());
        stack.getOrCreateChildTag(BLOCK_STATE_TAG).putString(property.getName(), value);
        stack.setDisplayName(stack.getDisplayName().appendText(property.getName() + "=" + value));
        return stack;
    }

    public static <T extends Item> Optional<T> toItem(Item item, Class<T> t) {
        return t.isInstance(item) ? Optional.of(t.cast(item)) : Optional.empty();
    }

    public static Optional<BlockItem> toItemBlock(Item item) {
        return toItem(item, BlockItem.class);
    }

    public static NonNullList<ItemStack> getFamilyItems(ItemStack stack) {
        return getFamilyItems(stack, TypeFilter.ANY);
    }

    public static NonNullList<ItemStack> getFamilyItems(ItemStack stack, TypeFilter filter) {
        NonNullList<ItemStack> items = NonNullList.create();
        getFamily(stack).addAllItems(ItemGroup.SEARCH, items, filter);
        if (items.isEmpty()) {
            items.add(stack);
        }
        return items;
    }

    public static Family<Block> getFamily(ItemStack stack) {
        Item item = stack.getItem();
        Block block = Blocks.AIR;
        if (item instanceof BlockItem) {
            block = ((BlockItem) item).getBlock();
        }
        return FamilyRegistry.BLOCKS.getFamily(block);
    }
}
