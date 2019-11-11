package com.conquestreforged.client;

import com.conquestreforged.client.palette.PaletteBindListener;
import com.conquestreforged.client.search.SearchBindListener;
import com.conquestreforged.core.client.input.Bindings;
import com.conquestreforged.core.block.playertoggle.ToggleBindListener;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientInit {

    private static final String category = "key.category.conquest";

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        Bindings.listen("Block Toggle", "key.keyboard.b", category, new ToggleBindListener());

        Bindings.listen("Palette GUI", "key.keyboard.c", category, new PaletteBindListener());

        Bindings.listen("Search", "key.keyboard.v", category, new SearchBindListener());
    }
}
