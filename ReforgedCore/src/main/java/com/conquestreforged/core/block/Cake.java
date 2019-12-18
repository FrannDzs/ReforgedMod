package com.conquestreforged.core.block;

import com.conquestreforged.core.block.properties.PropertyVariant;
import net.minecraft.block.CakeBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IProperty;
import net.minecraft.util.NonNullList;

public class Cake extends CakeBlock implements PropertyVariant {

    public Cake(Properties properties) {
        super(properties);
    }

    @Override
    public IProperty<?> getVariantProperty() {
        return CakeBlock.BITES;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        PropertyVariant.fillGroup(this, items);
    }
}
