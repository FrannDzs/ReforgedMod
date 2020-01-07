package com.conquestreforged.client.palette.palette2;

import com.conquestreforged.client.palette.util.Render;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.CreativeCraftingListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CCreativeInventoryActionPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;

public class PaletteScreen2 extends ContainerScreen<PaletteContainer> {

    private static final ResourceLocation WHEEL = new ResourceLocation("conquest:textures/gui/wheel.png");
    private static final ResourceLocation MASK0 = new ResourceLocation("conquest:textures/gui/wheel_mask0.png");
    private static final ResourceLocation MASK1 = new ResourceLocation("conquest:textures/gui/wheel_mask1.png");

    private CreativeCraftingListener listener;

    public PaletteScreen2(PlayerEntity player, PaletteContainer container, PlayerInventory inventory) {
        super(container, inventory, new StringTextComponent("Palette Screen"));
        this.passEvents = true;
        player.openContainer = container;
    }

    @Override
    protected void init() {
        super.init();
        if (this.minecraft != null && minecraft.player != null) {
            minecraft.player.container.removeListener(listener);
            listener = new CreativeCraftingListener(minecraft);
            minecraft.player.container.addListener(listener);
        }
    }

    @Override
    public void removed() {
        super.removed();
        if (minecraft != null) {
            if (minecraft.player != null && minecraft.player.inventory != null) {
                minecraft.player.container.removeListener(this.listener);
            }
            minecraft.keyboardListener.enableRepeatEvents(false);
        }
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
    public void onClose() {
        super.onClose();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        renderBackground();
        int diam = (PaletteContainer.RADIUS + 44) * 2;
        Render.drawTexture(WHEEL, guiLeft, guiTop, diam, diam, 0, 0, diam, diam);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        try {
            super.render(mouseX, mouseY, partialTicks);
            playerInventory.player.container.detectAndSendChanges();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private void resize(int width, int height) {
        int centerOffset = PaletteContainer.RADIUS + 44;
        this.xSize = centerOffset * 2;
        this.ySize = centerOffset * 2;
        this.guiLeft = (width / 2) - centerOffset;
        this.guiTop = (height / 2) - centerOffset;
        getContainer().setPos(centerOffset, centerOffset);
    }

    public static PaletteScreen2 create(PlayerEntity player, ItemStack center, List<ItemStack> stacks) {
        PlayerInventory inventory = player.inventory;
        PaletteContainer container = new PaletteContainer(inventory, center, stacks);
        return new PaletteScreen2(player, container, inventory);
    }
}
