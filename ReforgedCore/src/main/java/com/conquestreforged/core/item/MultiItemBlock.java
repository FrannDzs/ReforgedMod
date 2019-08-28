package com.conquestreforged.core.item;

import com.conquestreforged.core.item.family.Family;
import com.conquestreforged.core.item.family.FamilyRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class MultiItemBlock extends BlockItem {

    public MultiItemBlock(Block block, Properties builder) {
        super(block, builder);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return getFamily().size() - 1;
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return getBlockForId(stack.getDamage()).getTranslationKey();
    }

    @Nullable
    protected BlockState getStateForPlacement(BlockItemUseContext context) {
        ItemStack stack = context.getItem();
        Block block = getBlockForId(stack.getDamage());
        BlockState state = block.getStateForPlacement(context);
        return state != null && canPlace(context, state) ? state : null;
    }

    public Family<Block> getFamily() {
        return FamilyRegistry.BLOCKS.getFamily(getBlock());
    }

    public Block getActiveBlock(ItemStack stack) {
        return getBlockForId(stack.getDamage());
    }

    public void setActiveBlock(ItemStack stack, Block block) {
        stack.setDamage(getIdForBlock(block));
    }

    public void setActiveBlock(ItemStack stack, Class<? extends Block> type) {
        getFamily().getMember(type).ifPresent(block -> setActiveBlock(stack, block));
    }

    private Block getBlockForId(int id) {
        return getFamily().getMember(id).orElse(getBlock());
    }

    private int getIdForBlock(Block block) {
        int index = getFamily().indexOf(block);
        return index < 0 ? 0 : index;
    }
}
