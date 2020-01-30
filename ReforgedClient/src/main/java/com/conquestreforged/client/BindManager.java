package com.conquestreforged.client;

import com.conquestreforged.client.bind.DebugBindListener;
import com.conquestreforged.client.bind.PaletteBindListener;
import com.conquestreforged.client.bind.SearchBindListener;
import com.conquestreforged.client.bind.ToggleBindListener;
import com.conquestreforged.core.client.input.Bindings;
import com.conquestreforged.core.util.log.Log;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BindManager {

    private static final String category = "key.category.conquest";

    private static KeyBinding palette;

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        Log.info("Registering keybinds");

        Bindings.create("Block Toggle", "key.keyboard.b", category, new ToggleBindListener());

        palette = Bindings.create("Palette GUI", "key.keyboard.c", category, new PaletteBindListener());

        Bindings.create("Search", "key.keyboard.v", category, new SearchBindListener());

        Bindings.create("Copy Block Info", "key.keyboard.x", category, new DebugBindListener());
    }

    public static KeyBinding getPaletteBind() {
        return palette;
    }
}
