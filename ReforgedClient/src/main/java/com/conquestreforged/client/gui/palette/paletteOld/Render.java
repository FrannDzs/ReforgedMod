package com.conquestreforged.client.gui.palette.paletteOld;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.SpectralArrowRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * @author dags <dags@dags.me>
 */
public class Render {

    public static void cleanup() {
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableBlend();
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.clearColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void beginMask(ResourceLocation texture, int left, int top, int width, int height, float u, float v, int umax, int vmax) {
        RenderSystem.color4f(1, 1, 1, 1);
        RenderSystem.enableTexture();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
        RenderSystem.depthFunc(GL11.GL_ALWAYS);
        RenderSystem.colorMask(false, false, false, true);
        RenderSystem.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.SRC_ALPHA);

        RenderSystem.pushMatrix();
        RenderSystem.translatef(0, 0, 1);
        drawTexture(texture, left, top, width, height, u, v, umax, vmax);
        RenderSystem.popMatrix();

        RenderSystem.disableTexture();
        RenderSystem.depthMask(false);
        RenderSystem.depthFunc(GL11.GL_GREATER);
        RenderSystem.colorMask(true, true, true, false);
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.DST_ALPHA);
    }

    public static void endMask() {
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.depthMask(true);
        RenderSystem.depthFunc(GL11.GL_LEQUAL);
        RenderSystem.disableDepthTest();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableTexture();
    }

    public static void beginItems() {
        RenderSystem.clear(GL11.GL_DEPTH_BUFFER_BIT, true);
        RenderSystem.enableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
        RenderSystem.depthFunc(GL11.GL_LEQUAL);
        Render.enableGUIStandardItemLighting();

        RenderSystem.pushMatrix();
        RenderSystem.translatef(0, 0, 100);
    }

    public static void endItems() {
        RenderSystem.popMatrix();

        RenderSystem.disableDepthTest();
        RenderHelper.disableStandardItemLighting();
        RenderSystem.clearColor(1, 1, 1, 1);
    }

    public static void beginSlot(int xPos, int yPos, float scale) {
        beginSlot(xPos, yPos, 0, scale);
    }

    public static void beginSlot(int xPos, int yPos, int zPos, float scale) {
        RenderSystem.pushMatrix();
        RenderSystem.translatef(xPos, yPos, zPos);
        RenderSystem.scalef(scale, scale, scale);
        RenderSystem.alphaFunc(GL11.GL_NOTEQUAL, 0);
    }

    public static void endSlot() {
        RenderSystem.popMatrix();
    }

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

    public static void drawCenteredString(int x, int y, int color, String string) {
        FontRenderer renderer = Minecraft.getInstance().fontRenderer;
        int offsetX = renderer.getStringWidth(string) / 2;
        renderer.drawStringWithShadow(string, x - offsetX, y, color);
    }

    public static void drawOverlays(ItemStack stack, int x, int y) {
        drawOverlays(stack, x, y, 0, 1);
    }

    public static void drawOverlays(ItemStack stack, int x, int y, int z, float scale) {
        if (stack != null) {
            RenderSystem.pushMatrix();
            RenderSystem.scalef(scale, scale, scale);
            RenderSystem.translatef(x, y, z);
            Minecraft.getInstance().getItemRenderer().renderItemOverlays(Minecraft.getInstance().fontRenderer, stack, 0, 0);
            RenderSystem.popMatrix();
        }
    }

    public static void drawItemStack(ItemStack stack, int x, int y) {
        drawItemStack(stack, x, y, 0);
    }

    public static void drawItemStack(ItemStack stack, int x, int y, int z) {
        if (stack != null) {
            RenderSystem.pushMatrix();
            RenderSystem.translatef(x, y, z);
            Minecraft.getInstance().getItemRenderer().renderItemIntoGUI(stack, 0, 0);
            RenderSystem.popMatrix();
        }
    }

    public static void drawItemStackHighlight(ItemStack stack, int x, int y, float scale, int color) {
        drawItemStackHighlight(stack, x, y, 0, scale, color);
    }

    public static void drawItemStackHighlight(ItemStack stack, int x, int y, int z, float scale, int color) {
        if (stack != null) {
            RenderHelper.disableStandardItemLighting();

            RenderSystem.pushMatrix();
            RenderSystem.scalef(scale, scale, 0);
            RenderSystem.translatef(x, y, z);
            RenderSystem.setupOutline();
            RenderSystem.disableBlend();
            RenderSystem.enableColorMaterial();
//            RenderSystem.colorMaterial(color, color);

            Minecraft.getInstance().getItemRenderer().renderItemIntoGUI(stack, 0, 0);

            RenderSystem.disableColorMaterial();
            RenderSystem.teardownOutline();
            RenderSystem.popMatrix();
        }
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
