package com.conquestreforged.client.gui.palette.screen;

import com.conquestreforged.client.gui.CustomCreativeScreen;
import com.conquestreforged.client.gui.palette.PaletteContainer;
import com.conquestreforged.client.gui.palette.paletteOld.Render;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class PaletteScreen extends CustomCreativeScreen<PaletteContainer> {

    private static final ResourceLocation HOTBAR = new ResourceLocation("minecraft:textures/gui/widgets.png");
    private static final ResourceLocation WHEEL = new ResourceLocation("conquest:textures/gui/wheel.png");
    private static final ResourceLocation MASK0 = new ResourceLocation("conquest:textures/gui/wheel_mask0.png");
    private static final ResourceLocation MASK1 = new ResourceLocation("conquest:textures/gui/wheel_mask1.png");

    private static final int SIZE = (PaletteContainer.RADIUS + 44) * 2;

    public PaletteScreen(PlayerEntity player, PlayerInventory inventory, PaletteContainer container) {
        super(container, inventory, new StringTextComponent("Palette Screen"));
        this.passEvents = true;
        player.openContainer = container;
    }

    @Override
    public void init(Minecraft mc, int width, int height) {
        super.init(mc, width, height);
        resize(width, height);
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        resize(width, height);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        // translucent black background
        renderBackground();

        // Wheel texture
        Render.drawTexture(WHEEL, guiLeft, guiTop, SIZE, SIZE, 0, 0, SIZE, SIZE);

        // Hotbar texture?
        drawHotbar();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        Slot slot = getSlotUnderMouse();
        if (minecraft != null && slot != null) {
            int top = SIZE + 4 - 25;
            int left = SIZE / 2;
            int color = 0xFFFFFF;
            String text = slot.getStack().getDisplayName().getFormattedText();
            drawCenteredString(minecraft.fontRenderer, text, left, top, color);
        }
    }

    @Override
    protected boolean isContainerSlot(Slot slot) {
        return slot.inventory == getContainer().getPaletteInventory();
    }

    private void drawHotbar() {
        if (minecraft != null) {
            int u = 0;
            int v = 0;
            int uMax = 182;
            int vMax = 22;
            int left = (width / 2) - (uMax / 2);
            int top = guiTop + SIZE - 10;
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            minecraft.getTextureManager().bindTexture(new ResourceLocation("textures/gui/widgets.png"));
            blit(left, top, u, v, uMax, vMax);
        }
    }

    private void resize(int width, int height) {
        int center = SIZE / 2;
        this.xSize = SIZE;
        this.ySize = SIZE;
        this.guiLeft = (width - SIZE) / 2;
        this.guiTop = (height - SIZE) / 2;
        getContainer().setPos(center, center);
    }
}
