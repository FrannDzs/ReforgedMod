package com.conquestreforged.core.block.properties;

import com.conquestreforged.core.block.StateUtils;
import com.conquestreforged.core.item.ItemUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.state.Property;
import net.minecraft.util.NonNullList;

public interface PropertyVariant {

    Property<?> getVariantProperty();

    static <B extends Block & PropertyVariant> void fillGroup(B block, NonNullList<ItemStack> stacks) {
        Property<?> property = block.getVariantProperty();
        for (Object value : property.getPossibleValues()) {
            BlockState state = StateUtils.with(block.defaultBlockState(), property, value.toString());
            ItemStack stack = ItemUtils.fromState(state, property);
            stacks.add(stack);
        }
    }
}
