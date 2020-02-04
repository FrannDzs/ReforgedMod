package com.conquestreforged.client.gui.dependency;

import com.conquestreforged.client.gui.dependency.screen.DependencyScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.List;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DependencyEvents {

    private final DependencyList list = DependencyList.load();

    @SubscribeEvent
    public void reload(TextureStitchEvent event) {
        refresh();
    }

    public void refresh() {
        List<Dependency> missing = list.getMissingDependencies();
        if (missing.isEmpty()) {
            return;
        }
        Minecraft.getInstance().deferTask(() -> {
            DependencyScreen screen = new DependencyScreen(missing);
            Minecraft.getInstance().displayGuiScreen(screen);
        });
    }

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        DependencyEvents events = new DependencyEvents();
        MinecraftForge.EVENT_BUS.register(event);
    }
}
