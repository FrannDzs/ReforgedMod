package com.conquestreforged.core.init;

import com.conquestreforged.core.asset.pack.PackFinder;
import com.conquestreforged.core.asset.pack.VirtualResourcepack;
import com.conquestreforged.core.block.data.BlockDataRegistry;
import com.conquestreforged.core.util.log.Log;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.resources.ResourcePackType;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;

@Mod.EventBusSubscriber
public class InitServer {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void server(FMLServerAboutToStartEvent event) {
        Log.info("Registering server resources");

        IResourceManager resourceManager = event.getServer().getResourceManager();
        ResourcePackList<?> resourcePackList = event.getServer().getResourcePacks();

        BlockDataRegistry.getInstance().getNamespaces().forEach(namespace -> {
            VirtualResourcepack.Builder builder = VirtualResourcepack.builder(namespace).type(ResourcePackType.SERVER_DATA);
            BlockDataRegistry.getInstance().getData(namespace).forEach(data -> data.addServerResources(builder));
            builder.build(resourceManager);
        });

        PackFinder.getInstance(ResourcePackType.SERVER_DATA).register(resourceManager, resourcePackList);
    }
}
