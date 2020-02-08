package com.conquestreforged.client.gui.painting.renderer;

import com.conquestreforged.entities.painting.art.Art;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class VanillaPaintingRenderer implements PaintingRenderer {
    @Override
    public void setup() {

    }

    @Override
    public void tearDown() {

    }

    @Override
    public void render(int x, int y, int w, int h, Art art) {
        TextureAtlasSprite sprite = art.getTexture();
        sprite.getAtlasTexture().bindTexture();

        AbstractGui.blit(x, y, 0, w, h, sprite);
    }
}
