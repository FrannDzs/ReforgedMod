package com.conquestreforged.entities.painting.art;

import com.conquestreforged.entities.painting.entity.ArtType;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * @author dags <dags@dags.me>
 */
public class ModArt implements Art {

    public static final List<Art> ALL;

    static {
        ArtType[] arts = ArtType.values();
        ImmutableList.Builder<Art> builder = ImmutableList.builder();
        for (ArtType art : arts) {
            builder.add(new ModArt(art));
        }
        ALL = builder.build();
    }

    private final ArtType art;

    public ModArt(ArtType art) {
        this.art = art;
    }

    @Override
    public int u() {
        return art.offsetX;
    }

    @Override
    public int v() {
        return art.offsetY;
    }

    @Override
    public int width() {
        return art.sizeX;
    }

    @Override
    public int height() {
        return art.sizeY;
    }

    @Override
    public int textureWidth() {
        return 256;
    }

    @Override
    public int textureHeight() {
        return 256;
    }

    @Override
    public String getName() {
        return art.shapeId;
    }

    @Override
    public String getDisplayName(String parent) {
        return art.getDisplayName(parent);
    }

    @Override
    public boolean matches(Object art) {
        return art == this.art;
    }
}
