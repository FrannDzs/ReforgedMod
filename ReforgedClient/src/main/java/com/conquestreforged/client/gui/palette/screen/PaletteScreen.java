package com.conquestreforged.client.gui.palette.screen;

import com.conquestreforged.client.gui.base.CustomCreativeScreen;
import com.conquestreforged.client.gui.palette.PaletteContainer;
import com.conquestreforged.client.gui.palette.paletteOld.Render;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class PaletteScreen extends CustomCreativeScreen<PaletteContainer> {

    private static final ResourceLocation WHEEL = new ResourceLocation("conquest:textures/gui/picker/wheel.png");
    private static final ResourceLocation MASK0 = new ResourceLocation("conquest:textures/gui/picker/wheel_mask0.png");
    private static final ResourceLocation MASK1 = new ResourceLocation("conquest:textures/gui/picker/wheel_mask1.png");

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
        drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

        setupRender();
        {
            final int mx = mouseX - guiLeft;
            final int my = mouseY - guiTop;
            // render radial slots
            getContainer().visitRadius(mx, my, (slot, depth) -> {
                float scale = slot.getScale(mx, my);
                renderSlot(slot, slot.getStyle(), mx, my, depth, scale);
            });
            // render center slot
            getContainer().visitCenter(slot -> {
                float scale = slot.getScale(mx, my);
                renderSlot(slot, slot.getStyle(), mx, my, 1F, scale);
            });
            // render hotbar
            getContainer().visitHotbar(slot -> renderSlot(slot, mx, my, 1F,1F));
            // render the dragged item
            renderDraggedItem(mx, my, 3F);
        }
        tearDownRender();

        // render display text
        drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        // translucent black background
        renderBackground();

        // Wheel texture
        Render.drawTexture(WHEEL, guiLeft, guiTop, SIZE, SIZE, 0, 0);

        // Render hotbar texture
        getContainer().getHotbar().renderBackground(this);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        if (minecraft == null) {
            return;
        }

        ItemStack display = playerInventory.getItemStack();
        if (display.isEmpty()) {
            Slot slot = getContainer().getClosestSlot(mouseX - guiLeft, mouseY - guiTop, true);
            if (slot == null) {
                return;
            }
            display = slot.getStack();
        }

        if (display.getItem() == Items.AIR) {
            return;
        }

        int top = (height / 2) + PaletteContainer.RADIUS + 22;
        int left = width / 2;
        int color = 0xFFFFFF;
        String text = display.getDisplayName().getFormattedText();
        drawCenteredString(minecraft.fontRenderer, text, left, top, color);
    }

    @Override
    protected boolean isContainerSlot(Slot slot) {
        return slot.inventory == getContainer().getPaletteInventory();
    }

    private void resize(int width, int height) {
        this.xSize = SIZE;
        this.ySize = SIZE;
        this.guiLeft = (width - SIZE) / 2;
        this.guiTop = (height - SIZE) / 2;
        getContainer().init(this);
    }
}
