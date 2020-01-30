package com.conquestreforged.core.client.render;

import com.conquestreforged.core.util.RenderLayer;
import com.conquestreforged.core.util.log.Log;
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
            for (RenderType type : RenderType.getBlockRenderTypes()) {
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
        if (layer != RenderLayer.SOLID && layer != RenderLayer.UNDEFINED) {
            RenderType type = getRenderType(layer);
            RenderTypeLookup.setRenderLayer(block, type);
            Log.debug("Registered render type for Block: {}={}", block.getRegistryName(), type);
        }
    }

    public void register(Fluid fluid, RenderLayer layer) {
        if (layer != RenderLayer.SOLID && layer != RenderLayer.UNDEFINED) {
            RenderType type = getRenderType(layer);
            RenderTypeLookup.setRenderLayer(fluid, type);
            Log.debug("Registered render type for Fluid: {}={}", fluid.getRegistryName(), type);
        }
    }

    public static RenderLayerHelper getInstance() {
        return instance;
    }
}
