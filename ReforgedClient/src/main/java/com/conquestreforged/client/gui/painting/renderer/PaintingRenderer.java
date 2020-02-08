package com.conquestreforged.client.gui.painting.renderer;

import com.conquestreforged.entities.painting.art.Art;

public interface PaintingRenderer {

    void setup();

    void tearDown();

    void render(int x, int y, int w, int h, Art art);
}
