package com.conquestreforged.core.client.render;

import com.conquestreforged.core.util.RenderLayer;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.fluid.Fluid;

import java.util.EnumMap;
import java.util.Map;

public class RenderLayerHelper {

    private static final RenderLayerHelper instance = new RenderLayerHelper();

    private final Map<RenderLayer, RenderType> types = new EnumMap<>(RenderLayer.class);

    private RenderLayerHelper() {
        for (RenderLayer layer : RenderLayer.values()) {
            if (layer == RenderLayer.UNDEFINED) {
                continue;
            }

            boolean match = false;
            // func_228661_n_ - getBlockRenderTypes ?
            for (RenderType type : RenderType.func_228661_n_()) {
                if (type.toString().equalsIgnoreCase(layer.name())) {
                    types.put(layer, type);
                    match = true;
                    break;
                }
            }

            if (!match) {
                throw new RuntimeException("No RenderType of RenderLayer: " + layer);
            }
        }
    }

    public RenderType getRenderType(RenderLayer layer) {
        return types.get(layer);
    }

    public void register(Block block, RenderLayer layer) {
        // solid is default
        if (layer != RenderLayer.SOLID) {
            RenderType type = getRenderType(layer);
            RenderTypeLookup.setRenderLayer(block, type);
        }
    }

    public void register(Fluid fluid, RenderLayer layer) {
        RenderType type = getRenderType(layer);
        RenderTypeLookup.setRenderLayer(fluid, type);
    }

    public static RenderLayerHelper getInstance() {
        return instance;
    }
}
