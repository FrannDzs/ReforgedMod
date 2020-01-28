package com.conquestreforged.client.gui.base;

import com.conquestreforged.client.gui.palette.paletteOld.Render;
import com.conquestreforged.client.gui.palette.screen.Style;
import com.conquestreforged.client.gui.palette.shape.FloatMath;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;

public abstract class CustomContainerScreen<T extends Container> extends ContainerScreen<T> {

    private Slot clickedSlot;
    private boolean isRightMouseClick;
    private boolean isOverSlot = false;

    public CustomContainerScreen(T screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    protected void onSlotClick(@Nullable Slot slot, int index, int button, ClickType type) {
        clickedSlot = slot;
        isRightMouseClick = button == 2; // ?
    }

    @Override
    protected void handleMouseClick(@Nullable Slot slot, int index, int button, ClickType type) {
        super.handleMouseClick(slot, index, button, type);
        onSlotClick(slot, index, button, type);
    }

    protected void setupRender() {
        isOverSlot = false;

        RenderSystem.disableRescaleNormal();
        RenderSystem.disableDepthTest();

        RenderSystem.pushMatrix();
        RenderSystem.translatef(guiLeft, guiTop, 0.0F);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableRescaleNormal();
        RenderSystem.glMultiTexCoord2f(33986, 240.0F, 240.0F);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    protected void tearDownRender() {
        RenderSystem.popMatrix();
        RenderSystem.enableDepthTest();
    }

    public void renderDraggedItem(int mx, int my, float depth) {
        int zlevel = FloatMath.round(depth * 200);
        ItemStack held = playerInventory.getItemStack();
        if (!held.isEmpty()) {
            this.setBlitOffset(0);
            this.itemRenderer.zLevel = 0;
            RenderSystem.pushMatrix();
            RenderSystem.translatef(mx, my, zlevel);
            RenderSystem.enableDepthTest();
            Render.drawItemStackHighlight(held, -8, -8, 1.075F, 0);
            this.itemRenderer.renderItemAndEffectIntoGUI(playerInventory.player, held, -8, -8);
            this.itemRenderer.renderItemOverlayIntoGUI(font, held, -8, -8, null);
            RenderSystem.popMatrix();
            this.setBlitOffset(0);
            this.itemRenderer.zLevel = 0F;
        }
    }

    public void renderSlot(Slot slot, int mx, int my, float depth, float scale) {
        renderSlot(slot, null, mx, my, depth, scale);
    }

    public void renderSlot(Slot slot, Style style, int mx, int my, float depth, float scale) {
        int x = slot.xPos + 8;
        int y = slot.yPos + 8;
        int zlevel = FloatMath.round(depth * 200);
        ItemStack itemstack = slot.getStack();

        RenderSystem.pushMatrix();
        RenderSystem.translatef(x, y, zlevel);
        RenderSystem.scalef(scale, scale, 1);
        RenderSystem.enableDepthTest();

        // set z-level
        this.setBlitOffset(0);
        this.itemRenderer.zLevel = 0;

        if (!isOverSlot) {
            // draw highlight
            ItemStack held = playerInventory.getItemStack();
            if (held.isEmpty() && style != null && isMouseOver(slot, mx, my, 11, scale)) {
                isOverSlot = true;
                Render.drawItemStackHighlight(itemstack, -8, -8, 1.08F, style.selectedColor);
            }
        }

        // draw item
        this.itemRenderer.renderItemAndEffectIntoGUI(playerInventory.player, itemstack, -8, -8);
        this.itemRenderer.renderItemOverlayIntoGUI(font, itemstack, -8, -8, null);

        RenderSystem.popMatrix();

        this.itemRenderer.zLevel = 0.0F;
        this.setBlitOffset(0);
    }

    public static boolean isMouseOver(Slot slot, int mx, int my, int size, float scale) {
        float delta = size * scale;
        return mx >= slot.xPos - delta && mx <= slot.xPos + delta && my >= slot.yPos - delta && my <= slot.yPos + delta;
    }

}
