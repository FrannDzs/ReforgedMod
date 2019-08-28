package com.conquestreforged.entities.painting;

import com.conquestreforged.entities.ModEntities;
import com.conquestreforged.entities.ModItems;
import com.conquestreforged.entities.painting.entity.TextureType;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

/**
 * @author dags <dags@dags.me>
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonProxy implements Proxy {

    public final NetworkHandler netHandler = new NetworkHandler();

    @SubscribeEvent
    public static void items(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(ModItems.conquestPainting);
        event.getRegistry().register(ModItems.vanillaPainting);
    }

    @SubscribeEvent
    public static void entities(RegistryEvent.Register<EntityType<?>> event) {
        for (int i = 0; i <= 9; i++) {
            TextureType.register("painting" + i);
        }
        event.getRegistry().register(ModEntities.PAINTING);
    }

    @SubscribeEvent
    public static void commands(FMLServerStartingEvent event) {
        // TODO
    }
}
