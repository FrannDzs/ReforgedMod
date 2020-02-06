package com.conquestreforged.entities.painting.client.render;

import com.conquestreforged.entities.painting.entity.ArtType;
import com.conquestreforged.entities.painting.entity.PaintingEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

/**
 * @author dags <dags@dags.me>
 */
public class PaintingRenderer extends EntityRenderer<PaintingEntity> {

    private long time = 0L;

    public PaintingRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(PaintingEntity painting, float yaw, float ticks, MatrixStack matrix, IRenderTypeBuffer buffer, int light) {
        matrix.push();
        matrix.rotate(Vector3f.YP.rotationDegrees(180.0F - yaw));
        matrix.scale(0.0625F, 0.0625F, 0.0625F);
        ArtType art = painting.getArt();
        ResourceLocation texture = getEntityTexture(painting);
        RenderType renderType = RenderType.entityCutout(texture);
        IVertexBuilder builder = buffer.getBuffer(renderType);
        render(matrix, builder, painting, art.sizeX, art.sizeY, art.offsetX, art.offsetY);
        matrix.pop();
        super.render(painting, yaw, ticks, matrix, buffer, light);
    }

    @Override
    public ResourceLocation getEntityTexture(PaintingEntity entity) {
        return entity.getPaintingType().getResourceLocation();
    }

    private void render(MatrixStack matrix, IVertexBuilder builder, PaintingEntity entity, int width, int height, int textureU, int textureV) {
        MatrixStack.Entry entry = matrix.getLast();
        Matrix4f position = entry.getPositionMatrix();
        Matrix3f normals = entry.getNormalMatrix();

        float xCenter = (float) (-width) / 2.0F;
        float yCenter = (float) (-height) / 2.0F;
        for (int x = 0; x < width / 16; ++x) {
            for (int y = 0; y < height / 16; ++y) {
                float minX = xCenter + (float) (x * 16);
                float minY = yCenter + (float) (y * 16);
                float maxX = xCenter + (float) ((x + 1) * 16);
                float maxY = yCenter + (float) ((y + 1) * 16);

                int lightX = MathHelper.floor(entity.getPosX());
                int lightY = MathHelper.floor(entity.getPosY() + (double) ((maxY + minY) / 2.0F / 16.0F));
                int lightZ = MathHelper.floor(entity.getPosZ());
                int light = WorldRenderer.getCombinedLight(entity.world, new BlockPos(lightX, lightY, lightZ));

                float txMin = (float) (textureU + width - x * 16) / 256.0F;
                float txMax = (float) (textureU + width - (x + 1) * 16) / 256.0F;
                float tyMin = (float) (textureV + height - y * 16) / 256.0F;
                float tyMax = (float) (textureV + height - (y + 1) * 16) / 256.0F;
                vertex(position, normals, builder, maxX, minY, txMax, tyMin, 0.2F, 0, 0, -1, light);
                vertex(position, normals, builder, minX, minY, txMin, tyMin, 0.2F, 0, 0, -1, light);
                vertex(position, normals, builder, minX, maxY, txMin, tyMax, 0.2F, 0, 0, -1, light);
                vertex(position, normals, builder, maxX, maxY, txMax, tyMax, 0.2F, 0, 0, -1, light);

                // reverse the texture so it appears flipped on the back side height the paintings
                float txMinReverse = txMin;
                float txMaxReverse = txMax;
                float tyMinReverse = tyMax;
                float tyMaxReverse = tyMin;
//                vertex(position, normals, builder, maxX, maxY, txMaxReverse, tyMinReverse, 0.2F, 0, 0, -1, light);
//                vertex(position, normals, builder, minX, maxY, txMinReverse, tyMinReverse, 0.2F, 0, 0, -1, light);
//                vertex(position, normals, builder, minX, minY, txMinReverse, tyMaxReverse, 0.2F, 0, 0, -1, light);
//                vertex(position, normals, builder, maxX, minY, txMaxReverse, tyMaxReverse, 0.2F, 0, 0, -1, light);
            }
        }
    }

    private void vertex(Matrix4f position, Matrix3f normals, IVertexBuilder builder, float x, float y, float u, float v, float z, int nx, int ny, int nz, int light) {
        builder.pos(position, x, y, z)
                .color(255, 255, 255, 255)
                .tex(u, v)
                .overlay(OverlayTexture.DEFAULT_LIGHT)
                .lightmap(light)
                .normal(normals, (float) nx, (float) ny, (float) nz)
                .endVertex();
    }
}
