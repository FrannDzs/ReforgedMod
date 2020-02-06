package com.conquestreforged.entities.painting.init;

import com.conquestreforged.entities.ModEntities;
import com.conquestreforged.entities.ModItems;
import com.conquestreforged.entities.painting.client.render.PaintingRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Client {

    @SubscribeEvent
    public static void setup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.PAINTING, PaintingRenderer::new);
        registerModel(ModItems.conquestPainting, "conquest:painting");
        registerModel(ModItems.vanillaPainting, "minecraft:painting");
    }

    private static void registerModel(Item item, String id) {
        ModelResourceLocation modelLocation = new ModelResourceLocation(id, "inventory");
        Minecraft.getInstance().getItemRenderer().getItemModelMesher().register(item, modelLocation);
    }
}
