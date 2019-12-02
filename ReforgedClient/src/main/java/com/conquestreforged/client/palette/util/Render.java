package com.conquestreforged.client.palette.util;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * @author dags <dags@dags.me>
 */
public class Render {

    public static void cleanup() {
        GlStateManager.depthMask(true);
        GlStateManager.enableDepthTest();
        GlStateManager.enableAlphaTest();
        GlStateManager.enableBlend();
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.clearColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void beginMask(ResourceLocation texture, int left, int top, int width, int height, float u, float v, float umax, float vmax) {
        GlStateManager.color4f(1, 1, 1, 1);
        GlStateManager.enableTexture();
        GlStateManager.enableDepthTest();
        GlStateManager.depthMask(true);
        GlStateManager.depthFunc(GL11.GL_ALWAYS);
        GlStateManager.colorMask(false, false, false, true);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.SRC_ALPHA);

        GlStateManager.pushMatrix();
        GlStateManager.translatef(0, 0, 1);
        drawTexture(texture, left, top, width, height, u, v, umax, vmax);
        GlStateManager.popMatrix();

        GlStateManager.disableTexture();
        GlStateManager.depthMask(false);
        GlStateManager.depthFunc(GL11.GL_GREATER);
        GlStateManager.colorMask(true, true, true, false);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.DST_ALPHA);
    }

    public static void endMask() {
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.depthMask(true);
        GlStateManager.depthFunc(GL11.GL_LEQUAL);
        GlStateManager.disableDepthTest();
        GlStateManager.enableAlphaTest();
        GlStateManager.enableTexture();
    }

    public static void beginItems() {
        GlStateManager.clear(GL11.GL_DEPTH_BUFFER_BIT, true);
        GlStateManager.enableBlend();
        GlStateManager.enableAlphaTest();
        GlStateManager.enableDepthTest();
        GlStateManager.depthMask(true);
        GlStateManager.depthFunc(GL11.GL_LEQUAL);
        RenderHelper.enableGUIStandardItemLighting();

        GlStateManager.pushMatrix();
        GlStateManager.translatef(0, 0, 100);
    }

    public static void endItems() {
        GlStateManager.popMatrix();

        GlStateManager.disableDepthTest();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.clearColor(1, 1, 1, 1);
    }

    public static void beginSlot(int xPos, int yPos, float scale) {
        beginSlot(xPos, yPos, 0, scale);
    }

    public static void beginSlot(int xPos, int yPos, int zPos, float scale) {
        GlStateManager.pushMatrix();
        GlStateManager.translatef(xPos, yPos, zPos);
        GlStateManager.scalef(scale, scale, scale);
        GlStateManager.alphaFunc(GL11.GL_NOTEQUAL, 0);
    }

    public static void endSlot() {
        GlStateManager.popMatrix();
    }

    public static void drawTexture(ResourceLocation texture, int left, int top, int width, int height, float u, float v, float umax, float vwidth) {
        Minecraft.getInstance().getTextureManager().bindTexture(texture);
        AbstractGui.blit(left, top, u, v, width, height, width, height);
    }

    public static void drawItemStack(ItemStack stack, int x, int y) {
        if (stack == null) {
            return;
        }

        Minecraft.getInstance().getItemRenderer().renderItemIntoGUI(stack, x, y);
    }

    public static void drawOverlays(ItemStack stack, int x, int y) {
        if (stack == null) {
            return;
        }

        GlStateManager.pushMatrix();
        GlStateManager.translatef(x, y, 0);
        Minecraft.getInstance().getItemRenderer().renderItemOverlays(Minecraft.getInstance().fontRenderer, stack, -8, -8);
        GlStateManager.popMatrix();
    }

    public static void drawHighlightedItemStack(ItemStack stack, int x, int y, float scale, int color) {
        if (stack == null) {
            return;
        }

        GlStateManager.pushMatrix();
        GlStateManager.translatef(x, y, 0);
        GlStateManager.scalef(scale, scale, 0);
        GlStateManager.enableColorMaterial();
        GlStateManager.setupSolidRenderingTextureCombine(color);
        GlStateManager.disableBlend();
        Minecraft.getInstance().getItemRenderer().renderItemIntoGUI(stack, -8, -8);
        GlStateManager.tearDownSolidRenderingTextureCombine();
        GlStateManager.disableColorMaterial();
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.translatef(x, y, 0);
        Minecraft.getInstance().getItemRenderer().renderItemIntoGUI(stack, 0, 0);
        GlStateManager.popMatrix();
    }

    public static void beginTooltips() {
        GlStateManager.pushMatrix();
        GlStateManager.translatef(0, 0, 300);
    }

    public static void endTooltips() {
        GlStateManager.popMatrix();
    }
}
