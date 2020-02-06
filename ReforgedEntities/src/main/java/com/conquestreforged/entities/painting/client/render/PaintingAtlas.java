package com.conquestreforged.entities.painting.client.render;

import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class PaintingAtlas {

    private final AtlasTexture texture;

    public PaintingAtlas(TextureManager manager, ResourceLocation location) {
        texture = new AtlasTexture(location);
        manager.loadTexture(texture.getBasePath(), texture);
    }
}
