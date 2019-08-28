package com.conquestreforged.core.init;

import com.conquestreforged.core.asset.pack.PackFinder;
import com.conquestreforged.core.proxy.Proxies;
import com.conquestreforged.core.proxy.Side;
import com.conquestreforged.core.proxy.impl.ServerProxy;
import com.conquestreforged.core.util.Log;
import net.minecraft.resources.ResourcePackType;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

@Mod.EventBusSubscriber
public class InitEventsServer {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void starting(FMLServerAboutToStartEvent event) {
        Log.info("server about to start");
        Proxies.set(Side.SERVER, new ServerProxy(event.getServer()).registerListeners());
        PackFinder.getInstance(ResourcePackType.SERVER_DATA).register();
    }

    @SubscribeEvent
    public static void started(FMLServerStartedEvent event) {
        Log.info("server started");
    }

    @SubscribeEvent
    public static void stopping(FMLServerStoppingEvent event) {
        Log.info("server stopping");
    }
}
