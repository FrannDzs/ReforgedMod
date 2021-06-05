package com.conquestreforged.client.gui.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.*;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.pipeline.LightUtil;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.Random;

public class ModelRender {

    private static final int[] lightmap = {15728880, 15728880, 15728880, 15728880};

    public static void renderModel(IBakedModel model, int x, int y, int color) {
        renderModel(ItemCameraTransforms.TransformType.GUI, model, x, y, color);
    }

    public static void renderModel(ItemCameraTransforms.TransformType transform, IBakedModel model, int x, int y, int color) {
        RenderSystem.pushMatrix();
        Minecraft.getInstance().getTextureManager().bind(AtlasTexture.LOCATION_BLOCKS);
        Minecraft.getInstance().getTextureManager().getTexture(AtlasTexture.LOCATION_BLOCKS).setFilter(false, false);
        RenderSystem.enableRescaleNormal();
        RenderSystem.enableAlphaTest();
        RenderSystem.defaultAlphaFunc();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.translatef((float) x, (float) y, 100.0F);
        RenderSystem.translatef(8.0F, 8.0F, 0.0F);
        RenderSystem.scalef(1.0F, -1.0F, 1.0F);
        RenderSystem.scalef(16.0F, 16.0F, 16.0F);
        MatrixStack matrixstack = new MatrixStack();
        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        model = ForgeHooksClient.handleCameraTransforms(matrixstack, model, transform, false);

        boolean flag = !model.usesBlockLight();
        if (flag) {
            RenderHelper.setupForFlatItems();
        }

        renderModel(matrixstack, RenderType.cutout(), buffer, model, color);

        buffer.endBatch();
        RenderSystem.enableDepthTest();
        if (flag) {
            RenderHelper.setupFor3DItems();
        }

        RenderSystem.disableAlphaTest();
        RenderSystem.disableRescaleNormal();
        RenderSystem.popMatrix();
    }

    public static void renderModel(BlockState state, IBakedModel model, int x, int y, int color) {
        RenderSystem.pushMatrix();
        RenderSystem.enableTexture();
        Minecraft.getInstance().getTextureManager().bind(AtlasTexture.LOCATION_BLOCKS);
        Minecraft.getInstance().getTextureManager().getTexture(AtlasTexture.LOCATION_BLOCKS).setFilter(false, false);
        RenderSystem.enableRescaleNormal();
        RenderSystem.enableAlphaTest();
        RenderSystem.defaultAlphaFunc();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.translatef((float) x, (float) y, 100.0F);
        RenderSystem.translatef(8.0F, 8.0F, 0.0F);
        RenderSystem.scalef(1.0F, -1.0F, 1.0F);
        RenderSystem.scalef(16.0F, 16.0F, 16.0F);
        MatrixStack matrix = new MatrixStack();

        matrix.pushPose();
        matrix.translate(-0.75, 0, 0);
        matrix.mulPose(new Quaternion(30, 30, 0, true));
        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, matrix, buffer, 15728880, OverlayTexture.NO_OVERLAY);
        matrix.popPose();

        buffer.endBatch();
        RenderSystem.enableDepthTest();

        RenderSystem.disableAlphaTest();
        RenderSystem.disableRescaleNormal();
        RenderSystem.popMatrix();
    }

    private static void renderModel(MatrixStack matrix, RenderType rendertype, IRenderTypeBuffer buffer, IBakedModel model, int color) {
        matrix.pushPose();
        matrix.translate(-0.5D, -0.5D, -0.5D);
        IVertexBuilder builder = getBuffer(buffer, rendertype, true, false);
        renderModel(model, matrix, builder, color);
        matrix.popPose();
    }

    private static void renderModel(IBakedModel modelIn, MatrixStack matrix, IVertexBuilder buffer, int color) {
        Random random = new Random();
        long i = 42L;

        for (Direction direction : Direction.values()) {
            random.setSeed(42L);
            renderQuads(matrix, buffer, modelIn.getQuads(null, direction, random), color);
        }

        random.setSeed(42L);
        renderQuads(matrix, buffer, modelIn.getQuads(null, null, random), color);
    }

    private static void renderQuads(MatrixStack matrix, IVertexBuilder buffer, List<BakedQuad> quads, int color) {
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;

        MatrixStack.Entry entry = matrix.last();
        for (BakedQuad bakedquad : quads) {
            render(buffer, bakedquad, entry, r, g, b, 1);
        }
    }

    private static void render(IVertexBuilder bufferIn, BakedQuad quadIn, MatrixStack.Entry entry, float red, float green, float blue, float alpha) {
        int[] aint = quadIn.getVertices();
        Vector3i vec3i = quadIn.getDirection().getNormal();
        Vector3f vector3f = new Vector3f((float) vec3i.getX(), (float) vec3i.getY(), (float) vec3i.getZ());
        Matrix4f matrix4f = entry.pose();
        vector3f.transform(entry.normal());
        int i = 8;
        int j = aint.length / 8;

        try (MemoryStack memorystack = MemoryStack.stackPush()) {
            ByteBuffer bytebuffer = memorystack.malloc(DefaultVertexFormats.BLOCK.getVertexSize());
            IntBuffer intbuffer = bytebuffer.asIntBuffer();

            for (int k = 0; k < j; ++k) {
                intbuffer.clear();
                intbuffer.put(aint, k * 8, 8);
                float f = bytebuffer.getFloat(0);
                float f1 = bytebuffer.getFloat(4);
                float f2 = bytebuffer.getFloat(8);
                int l = Math.min(k, lightmap.length - 1);
                int light = applyBakedLighting(lightmap[l], bytebuffer);
                float u = bytebuffer.getFloat(16);
                float v = bytebuffer.getFloat(20);
                Vector4f vector4f = new Vector4f(f, f1, f2, 1.0F);
                vector4f.transform(matrix4f);
                applyBakedNormals(vector3f, bytebuffer, entry.normal());
                bufferIn.vertex(vector4f.x(), vector4f.y(), vector4f.z(), red, green, blue, alpha, u, v, OverlayTexture.NO_OVERLAY, light, vector3f.x(), vector3f.y(), vector3f.z());
            }
        }
    }

    private static int applyBakedLighting(int lightmapCoord, ByteBuffer data) {
        int bl = LightTexture.block(lightmapCoord);
        int sl = LightTexture.sky(lightmapCoord);
        int offset = LightUtil.getLightOffset(0) * 4; // int offset for vertex 0 * 4 bytes per int
        int blBaked = Short.toUnsignedInt(data.getShort(offset)) >> 4;
        int slBaked = Short.toUnsignedInt(data.getShort(offset + 2)) >> 4;
        bl = Math.max(bl, blBaked);
        sl = Math.max(sl, slBaked);
        return LightTexture.pack(bl, sl);
    }

    private static void applyBakedNormals(Vector3f generated, ByteBuffer data, Matrix3f normalTransform) {
        byte nx = data.get(28);
        byte ny = data.get(29);
        byte nz = data.get(30);
        if (nx != 0 || ny != 0 || nz != 0) {
            generated.set(nx / 127f, ny / 127f, nz / 127f);
            generated.transform(normalTransform);
        }
    }

    public static IVertexBuilder getBuffer(IRenderTypeBuffer buffer, RenderType type, boolean isItemIn, boolean dummy) {
        return buffer.getBuffer(type);
    }
}
