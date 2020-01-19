package com.conquestreforged.core.init;

import com.conquestreforged.core.asset.lang.VirtualLang;
import com.conquestreforged.core.asset.pack.PackFinder;
import com.conquestreforged.core.asset.pack.VirtualResourcepack;
import com.conquestreforged.core.block.data.BlockData;
import com.conquestreforged.core.block.data.BlockDataRegistry;
import com.conquestreforged.core.block.data.ColorType;
import com.conquestreforged.core.client.color.BlockColors;
import com.conquestreforged.core.proxy.Proxies;
import com.conquestreforged.core.proxy.Side;
import com.conquestreforged.core.proxy.impl.ClientProxy;
import com.conquestreforged.core.util.Log;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.resources.ResourcePackType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class InitEventsClient {

    @SubscribeEvent // use this event as it happens later in the registry event cycle, but before first resource reload
    public static void recipes(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        Log.debug("Registering client resourcepack");
        Proxies.set(Side.CLIENT, new ClientProxy());

        Side.CLIENT.getProxy().registerListeners();

        // init client virtual resources (assets)
        BlockDataRegistry.getInstance().getNamespaces().forEach(namespace -> {
            VirtualResourcepack.Builder builder = VirtualResourcepack.builder(namespace).type(ResourcePackType.CLIENT_RESOURCES);
            BlockDataRegistry.getInstance().getData(namespace).forEach(data -> data.addClientResources(builder));
            builder.add(new VirtualLang(namespace));
            builder.build();
        });

        PackFinder.getInstance(ResourcePackType.CLIENT_RESOURCES).register();
    }

    @SubscribeEvent
    public static void blockColors(ColorHandlerEvent.Block event) {
        Log.debug("Registering block colors");
        for (BlockData data : BlockDataRegistry.getInstance()) {
            if (data.getProps().getColorType() == ColorType.GRASS) {
                event.getBlockColors().register(BlockColors.GRASS, data.getBlock());
            } else if (data.getProps().getColorType() == ColorType.FOLIAGE) {
                event.getBlockColors().register(BlockColors.FOLIAGE, data.getBlock());
            } else if (data.getProps().getColorType() == ColorType.WATER) {
                event.getBlockColors().register(BlockColors.WATER, data.getBlock());
            }
        }
    }

    @SubscribeEvent
    public static void itemColors(ColorHandlerEvent.Item event) {
        Log.debug("Registering item colors");
        IItemColor itemColor = BlockColors.toItemColor(event.getBlockColors());
        for (BlockData data : BlockDataRegistry.getInstance()) {
            if (data.getProps().getColorType() == ColorType.GRASS || data.getProps().getColorType() == ColorType.FOLIAGE) {
                event.getItemColors().register(itemColor, data.getBlock());
            }
        }
    }

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        Log.debug("Registering block render layers");
        BlockDataRegistry.getInstance().forEach(BlockData::addRenders);
    }
}
