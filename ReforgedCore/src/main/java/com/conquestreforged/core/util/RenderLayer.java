package com.conquestreforged.core.util;

import net.minecraft.client.renderer.RenderType;

public enum RenderLayer {
    UNDEFINED,
    SOLID,
    CUTOUT,
    CUTOUT_MIPPED,
    TRANSLUCENT,
    ;

    public boolean isDefault() {
        return this == UNDEFINED || this == SOLID;
    }

    public boolean isCutout() {
        return this == CUTOUT || this == CUTOUT_MIPPED;
    }

    public RenderType getRenderType() {
        switch (this) {
            case SOLID:
                return RenderType.solid();
            case CUTOUT:
                return RenderType.cutout();
            case TRANSLUCENT:
                return RenderType.translucent();
            case CUTOUT_MIPPED:
                return RenderType.cutoutMipped();
        }
        return null;
    }
}
