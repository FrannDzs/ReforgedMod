package com.conquestreforged.core.client.render;

import com.conquestreforged.core.util.RenderLayer;
import com.conquestreforged.core.util.log.Log;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.fluid.Fluid;

public class RenderLayerHelper {

    private static final RenderLayerHelper instance = new RenderLayerHelper();

    public void register(Block block, RenderLayer layer) {
        if (layer != RenderLayer.SOLID && layer != RenderLayer.UNDEFINED) {
            RenderType type = layer.getRenderType();
            if (type == null) {
                return;
            }
            RenderTypeLookup.setRenderLayer(block, type);
            Log.debug("Registered render type for Block: {}={}", block.getRegistryName(), type);
        }
    }

    public void register(Fluid fluid, RenderLayer layer) {
        if (layer != RenderLayer.SOLID && layer != RenderLayer.UNDEFINED) {
            RenderType type = layer.getRenderType();
            if (type == null) {
                return;
            }
            RenderTypeLookup.setRenderLayer(fluid, type);
            Log.debug("Registered render type for Fluid: {}={}", fluid.getRegistryName(), type);
        }
    }

    public static RenderLayerHelper getInstance() {
        return instance;
    }
}
