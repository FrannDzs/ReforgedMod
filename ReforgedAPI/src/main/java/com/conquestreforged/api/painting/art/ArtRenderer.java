package com.conquestreforged.api.painting.art;

import com.conquestreforged.api.painting.Painting;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.item.PaintingType;

public enum ArtRenderer {
    MOD {
        @Override
        public void render(Painting painting, Art<?> art, MatrixStack matrixStack, int x, int y, int w, int h) {
            Minecraft.getInstance().getTextureManager().bind(painting.getRegistryName());
            AbstractGui.blit(matrixStack, x, y, w, h, art.u(), art.v(), art.width(), art.height(), art.textureWidth(), art.textureHeight());
        }
    },
    VANILLA {
        @Override
        public void render(Painting painting, Art<?> art, MatrixStack matrixStack, int x, int y, int w, int h) {
            PaintingType type = (PaintingType) art.getReference();
            TextureAtlasSprite sprite = Minecraft.getInstance().getPaintingTextures().get(type);
            sprite.atlas().bind();
            AbstractGui.blit(matrixStack, x, y, 0, w, h, sprite);
        }
    },
    ;

    public abstract void render(Painting painting, Art<?> art, MatrixStack matrixStack, int x, int y, int w, int h);
}
