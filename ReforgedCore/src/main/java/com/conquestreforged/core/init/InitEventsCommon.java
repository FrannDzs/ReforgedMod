package com.conquestreforged.core.init;

import com.conquestreforged.core.asset.pack.VirtualResourcepack;
import com.conquestreforged.core.block.data.BlockDataRegistry;
import com.conquestreforged.core.util.Log;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.resources.ResourcePackType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class InitEventsCommon {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void blocks(RegistryEvent.Register<Block> event) {
        Log.info("Registering blocks");
        BlockDataRegistry.BLOCK_DATA.forEach(data -> Registrar.register(data, data.getBlock(), event.getRegistry()));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void items(RegistryEvent.Register<Item> event) {
        Log.info("Registering block items");
        BlockDataRegistry.BLOCK_DATA.forEach(data -> Registrar.register(data, data.getItem(), event.getRegistry()));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void common(FMLCommonSetupEvent event) {
        // init common virtual resources (data)
        BlockDataRegistry.getNamespaces().forEach(namespace -> {
            VirtualResourcepack.Builder builder = VirtualResourcepack.builder(namespace).type(ResourcePackType.SERVER_DATA);
            BlockDataRegistry.getData(namespace).forEach(data -> data.addServerResources(builder));
            builder.build();
        });

        BlockStats stats = new BlockStats();
        Log.info("Block Stats:");
        Log.info("(Total) Blocks: {}, States: {}", stats.totalBlocks, stats.totalStates);
        Log.info("(Vanilla) Blocks: {}, States: {}", stats.vanillaBlocks, stats.vanillaStates);
        Log.info("(Conquest) Blocks: {}, States: {}", stats.conquestBlocks, stats.conquestStates);

//        PackFinder.export(new File("data"), true);
    }
}
