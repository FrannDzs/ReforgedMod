package com.conquestreforged.core.init;

import com.conquestreforged.core.asset.lang.VirtualLang;
import com.conquestreforged.core.asset.pack.PackFinder;
import com.conquestreforged.core.asset.pack.VirtualResourcepack;
import com.conquestreforged.core.block.data.BlockDataRegistry;
import com.conquestreforged.core.item.group.manager.ItemGroupManager;
import com.conquestreforged.core.proxy.Proxies;
import com.conquestreforged.core.proxy.Side;
import com.conquestreforged.core.proxy.impl.ClientProxy;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.ResourcePackType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

import java.util.LinkedList;
import java.util.List;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class InitEventsClient {

    private static final List<IFutureReloadListener> reloadListeners = new LinkedList<>();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void models(ModelRegistryEvent event) {
        // ?
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void client(FMLClientSetupEvent event) {
        Proxies.set(Side.CLIENT, new ClientProxy());

        // init client virtual resources (assets)
        BlockDataRegistry.getNamespaces().forEach(namespace -> {
            VirtualResourcepack.Builder builder = VirtualResourcepack.builder(namespace).type(ResourcePackType.CLIENT_RESOURCES);
            BlockDataRegistry.getData(namespace).forEach(data -> data.addClientResources(builder));
            builder.add(new VirtualLang(namespace));
            builder.build();
        });

        PackFinder.getInstance(ResourcePackType.CLIENT_RESOURCES).register();

        // init ItemGroup manager
        ItemGroupManager.getInstance().init();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void common(FMLLoadCompleteEvent event) {
        Side.CLIENT.getProxy().registerListeners();
    }
}
