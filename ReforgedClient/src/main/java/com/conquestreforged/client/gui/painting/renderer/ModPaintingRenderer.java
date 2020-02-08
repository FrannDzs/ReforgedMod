package com.conquestreforged.client.gui.painting.renderer;

import com.conquestreforged.entities.painting.art.Art;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;

public class ModPaintingRenderer implements PaintingRenderer {

    private final ResourceLocation location;

    public ModPaintingRenderer(ResourceLocation location) {
        this.location = location;
    }

    @Override
    public void setup() {
        Minecraft.getInstance().getTextureManager().bindTexture(location);
    }

    @Override
    public void tearDown() {

    }

    @Override
    public void render(int x, int y, int w, int h, Art art) {
        AbstractGui.blit(x, y, w, h, art.u(), art.v(), art.width(), art.height(), art.textureWidth(), art.textureHeight());
    }
}
