package com.conquestreforged.core.item.group.manager;

import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class DelegateGroup extends ItemGroup {

    private final ItemGroup group;

    DelegateGroup(ItemGroup group) {
        super(-1, group.getRecipeFolderName());
        this.group = group;
    }

    @Override
    public String getRecipeFolderName() {
        return group.getRecipeFolderName();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ITextComponent getDisplayName() {
        return group.getDisplayName();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getIconItem() {
        return group.getIconItem();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack makeIcon() {
        return group.makeIcon();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public String getBackgroundSuffix() {
        return group.getBackgroundSuffix();
    }

    @Override
    public ItemGroup setBackgroundSuffix(String texture) {
        return group.setBackgroundSuffix(texture);
    }

    @Override
    public ItemGroup setRecipeFolderName(String pathIn) {
        return group.setRecipeFolderName(pathIn);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean showTitle() {
        return group.showTitle();
    }

    @Override
    public ItemGroup hideTitle() {
        return group.hideTitle();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean canScroll() {
        return group.canScroll();
    }

    @Override
    public ItemGroup hideScroll() {
        return group.hideScroll();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getColumn() {
        return super.getColumn();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isTopRow() {
        return super.isTopRow();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isAlignedRight() {
        return super.isAlignedRight();
    }

    @Override
    public EnchantmentType[] getEnchantmentCategories() {
        return group.getEnchantmentCategories();
    }

    @Override
    public ItemGroup setEnchantmentCategories(EnchantmentType... types) {
        return group.setEnchantmentCategories(types);
    }

    @Override
    public boolean hasEnchantmentCategory(@Nullable EnchantmentType enchantmentType) {
        return group.hasEnchantmentCategory(enchantmentType);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void fillItemList(NonNullList<ItemStack> items) {
        if (group == ItemGroup.TAB_HOTBAR) {
            return;
        }
        group.fillItemList(items);
    }

    @Override
    public int getTabPage() {
        return super.getTabPage();
    }

    @Override
    public boolean hasSearchBar() {
        return group.hasSearchBar();
    }

    @Override
    public int getSearchbarWidth() {
        return group.getSearchbarWidth();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getBackgroundImage() {
        return group.getBackgroundImage();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getTabsImage() {
        return group.getTabsImage();
    }

    @Override
    public int getLabelColor() {
        return group.getLabelColor();
    }

    @Override
    public int getSlotColor() {
        return group.getSlotColor();
    }
}
