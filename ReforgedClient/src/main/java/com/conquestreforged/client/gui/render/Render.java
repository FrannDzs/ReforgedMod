package com.conquestreforged.client.gui.render;

import com.conquestreforged.client.gui.palette.screen.Style;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * @author dags <dags@dags.me>
 */
public class Render {

    public static void drawTexture(ResourceLocation texture, int left, int top, int width, int height, float u, float v) {
        drawTexture(texture, left, top, width, height, u, v, width, height);
    }

    public static void drawTexture(ResourceLocation texture, int left, int top, int width, int height, float u, float v, int umax, int vmax) {
        Minecraft.getInstance().getTextureManager().bindTexture(texture);
        AbstractGui.blit(left, top, u, v, width, height, umax, vmax);
    }

    public static void drawTexture(ResourceLocation texture, int left, int top, int blitOffset, float u, float v, int umax, int vmax) {
        Minecraft.getInstance().getTextureManager().bindTexture(texture);
        AbstractGui.blit(left, top, blitOffset, u, v, umax, vmax, 256, 256);
    }

    public static void drawItemStackHighlight(ItemStack stack, int x, int y, Style style) {
        drawItemStackHighlight(stack, x, y, style.highlightScale, style.highlightColor);
    }

    public static void drawItemStackHighlight(ItemStack stack, int x, int y, float scale, int color) {
        RenderSystem.pushMatrix();
        RenderSystem.setupOutline();
        RenderSystem.scalef(scale, scale, 1F);
        IBakedModel model = Minecraft.getInstance().getItemRenderer().getItemModelMesher().getItemModel(stack);
        ModelRender.renderModel(model, x, y, color);
        RenderSystem.teardownOutline();
        RenderSystem.popMatrix();
    }

    public static void beginTooltips() {
        RenderSystem.pushMatrix();
        RenderSystem.translatef(0, 0, 300);
    }

    public static void endTooltips() {
        RenderSystem.popMatrix();
    }

    public static void setupSolidRenderingTextureCombine(int color) {
        // TODO
    }

    public static void tearDownSolidRenderingTextureCombine() {
        // TODO
    }

    public static void enableGUIStandardItemLighting() {
        RenderHelper.enableStandardItemLighting();
    }
}
