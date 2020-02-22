package com.conquestreforged.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.CreativeCraftingListener;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;

public abstract class PickerScreen<T> extends Screen {

    private final ItemStack stack;
    private final List<T> options;

    private int index;

    public PickerScreen(String title, ItemStack stack, T selected, List<T> options) {
        super(new StringTextComponent(title));
        this.stack = stack;
        this.options = options;
        this.index = options.indexOf(selected);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        Minecraft.getInstance().displayGuiScreen(null);
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean keyPressed(int typedChar, int keyCode, int what) {
        return super.keyPressed(typedChar, keyCode, what);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        renderBackground();

        int centerX = width / 2;
        int centerY = height / 2;

        GlStateManager.enableAlphaTest();
        GlStateManager.enableTexture();

        for (int i = 5; i >= 0; i--) {
            if (i > 0) {
                renderOption(centerX, centerY, -i);
            }
            renderOption(centerX, centerY, +i);
        }
        drawLabel(centerX, centerY);
    }

    @Override
    public boolean mouseScrolled(double mx, double my, double scroll) {
        if (scroll > 0) {
            index = index - 1;
            if (index < 0) {
                index = options.size() - 1;
            }
        }

        if (scroll < 0) {
            index = index + 1;
            if (index >= options.size()) {
                index = 0;
            }
        }
        return false;
    }

    @Override
    public void removed() {
        if (minecraft == null || minecraft.player == null || !minecraft.player.isCreative()) {
            return;
        }

        T option = options.get(index);
        int slot = minecraft.player.inventory.getSlotFor(stack);
        ItemStack stack = createItemStack(this.stack, option);

        CreativeCraftingListener listener = new CreativeCraftingListener(minecraft);
        minecraft.player.container.addListener(listener);
        minecraft.player.inventory.setInventorySlotContents(slot, stack);
        minecraft.player.container.detectAndSendChanges();
        minecraft.player.container.removeListener(listener);
    }

    private void renderOption(int cx, int cy, int di) {
        int index = this.index + di;

        if (index < 0) {
            index = (options.size() - 1) + index;
        }

        if (index >= options.size()) {
            index = index - (options.size() - 1);
        }

        if (index < 0 || index > options.size()) {
            return;
        }

        float scale0 = 2F - ((Math.abs(di)) / 4F);
        int size = Math.round((this.width / 11F) * scale0);
        int left = cx + 1 + (di * (size + 1)) - (size / 2);
        int top = cy - (size / 2);

        T option = options.get(index);
        float w = 1F;
        float h = 1F;
        int width = getWidth(option);
        int height = getHeight(option);
        if (width != height) {
            float scale1 = 1F / Math.max(width, height);
            w = width * scale1;
            h = height * scale1;
        }

        int tw = Math.round(size * w);
        int th = Math.round(size * h);
        int tl = left + ((size - tw) / 2);
        int tt = top + ((size - th) / 2);

        float alpha = Math.min(1F, 0.2F + Math.max(0, 1F - (Math.abs(di) / 2F)));
        GlStateManager.color4f(alpha, alpha, alpha, 1F);

        render(option, tl, tt, tw, th);
    }

    private void drawLabel(int centerX, int centerY) {
        T option = options.get(index);
        String text = getDisplayName(option);
        int width = font.getStringWidth(text);
        int height = (this.width / 11) + 10;
        font.drawStringWithShadow(text, centerX - (width / 2F), centerY + height, 0xFFFFFF);
    }

    public abstract int getWidth(T option);

    public abstract int getHeight(T option);

    public abstract String getDisplayName(T option);

    public abstract void render(T option, int x, int y, int width, int height);

    public abstract ItemStack createItemStack(ItemStack original, T value);
}
