package com.conquestreforged.entities.painting.init;

import com.conquestreforged.entities.ModEntities;
import com.conquestreforged.entities.ModItems;
import com.conquestreforged.entities.painting.entity.TextureType;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Common {

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
}
