package com.conquestreforged.client.gui.painting;

import com.conquestreforged.api.painting.Painting;
import com.conquestreforged.api.painting.art.Art;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.CreativeCraftingListener;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;

public class PaintingScreen extends Screen {

    private final ItemStack stack;
    private final Painting type;
    private final List<? extends Art<?>> arts;

    private int artIndex;
    private int hoveredIndex = -1;

    public PaintingScreen(ItemStack stack, Painting type, Art<?> art) {
        super(new StringTextComponent("Painting Selector"));
        this.type = type;
        this.stack = stack;
        this.arts = art.getAll();
        this.artIndex = art.getAll().indexOf(art);
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
//        Minecraft.getInstance().displayGuiScreen(null);
        return super.keyPressed(typedChar, keyCode, what);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        renderBackground();

        int centerX = width / 2;
        int centerY = height / 2;
        hoveredIndex = -1;

        GlStateManager.enableAlphaTest();
        GlStateManager.enableTexture();
        for (int i = 5; i >= 0; i--) {
            if (i > 0) {
                drawArt(mouseX, mouseY, centerX, centerY, -i);
            }
            drawArt(mouseX, mouseY, centerX, centerY, +i);
        }
        drawLabel(centerX, centerY);
    }

    @Override
    public void removed() {
        if (minecraft == null || minecraft.player == null || !minecraft.player.isCreative()) {
            return;
        }

        int artIndex = hoveredIndex == -1 ? this.artIndex : hoveredIndex;
        Art<?> art = arts.get(artIndex);

        int slot = minecraft.player.inventory.getSlotFor(stack);
        ItemStack stack = type.createStack(art);

        CreativeCraftingListener listener = new CreativeCraftingListener(minecraft);
        minecraft.player.container.addListener(listener);
        minecraft.player.inventory.setInventorySlotContents(slot, stack);
        minecraft.player.container.detectAndSendChanges();
        minecraft.player.container.removeListener(listener);
    }

    @Override
    public boolean mouseScrolled(double mx, double my, double scroll) {
        if (scroll > 0) {
            artIndex = artIndex - 1;
            if (artIndex < 0) {
                artIndex = arts.size() - 1;
            }
        }

        if (scroll < 0) {
            artIndex = artIndex + 1;
            if (artIndex >= arts.size()) {
                artIndex = 0;
            }
        }
        return false;
    }

    private void drawArt(int mx, int my, int cx, int cy, int di) {
        int index = artIndex + di;

        if (index < 0) {
            index = (arts.size() - 1) + index;
        }

        if (index >= arts.size()) {
            index = index - (arts.size() - 1);
        }

        float scale0 = 2F - ((Math.abs(di)) / 4F);
        int size = Math.round((this.width / 11F) * scale0);
        int left = cx + 1 + (di * (size + 1)) - (size / 2);
        int top = cy - (size / 2);

        Art<?> art = arts.get(index);
        float w = 1F;
        float h = 1F;
        if (art.width() != art.height()) {
            float scale1 = 1F / Math.max(art.width(), art.height());
            w = art.width() * scale1;
            h = art.height() * scale1;
        }

        int tw = Math.round(size * w);
        int th = Math.round(size * h);
        int tl = left + ((size - tw) / 2);
        int tt = top + ((size - th) / 2);

        handleMouse(mx, my, tl, tt, tw, th, index);

        float alpha = Math.min(1F, 0.2F + Math.max(0, 1F - (Math.abs(di) / 2F)));
        GlStateManager.color4f(alpha, alpha, alpha, 1F);

        art.getRenderer().render(type, art, tl, tt, tw, th);
    }

    private void drawLabel(int centerX, int centerY) {
        int index = hoveredIndex != -1 ? hoveredIndex : artIndex;
        Art<?> art = arts.get(index);
        String text = art.getDisplayName(type.getTranslationKey());
        int width = font.getStringWidth(text);
        int height = (this.width / 11) + 10;
        font.drawStringWithShadow(text, centerX - (width / 2F), centerY + height, 0xFFFFFF);
    }

    private void handleMouse(int mx, int my, int l, int t, int w, int h, int index) {
        if (mx >= l && mx <= l + w && my >= t && my <= t + h) {
            this.hoveredIndex = index;
        }
    }
}