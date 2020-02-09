package com.conquestreforged.client.gui.palette;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
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

    public static void drawItemStackHighlight(ItemStack stack, int x, int y, float scale, int color) {
        drawItemStackHighlight(stack, x, y, 0, scale, color);
    }

    public static void drawItemStackHighlight(ItemStack stack, int x, int y, int z, float scale, int color) {
        if (stack != null) {
            int dark = 0;
            int light = 15728888;

            RenderSystem.setupOutline();
            RenderSystem.pushMatrix();
            RenderSystem.scalef(scale, scale, 0);
            RenderSystem.translatef(x, y, z);

            ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
            RenderSystem.translatef(8.0F, 8.0F, 0.0F);
            RenderSystem.scalef(1.0F, -1.0F, 1.0F);
            RenderSystem.scalef(16.0F, 16.0F, 16.0F);
            MatrixStack matrixstack = new MatrixStack();
            IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
            IBakedModel model = renderer.getItemModelMesher().getItemModel(stack);
            renderer.renderItem(stack, ItemCameraTransforms.TransformType.GUI, false, matrixstack, buffer, dark, OverlayTexture.DEFAULT_LIGHT, model);
            buffer.finish();

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
