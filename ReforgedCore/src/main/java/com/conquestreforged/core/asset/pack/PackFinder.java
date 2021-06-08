package com.conquestreforged.core.asset.pack;

import com.conquestreforged.core.asset.meta.VirtualMeta;
import com.conquestreforged.core.util.log.Log;
import net.minecraft.resources.*;
import net.minecraft.resources.data.PackMetadataSection;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PackFinder implements IPackFinder {

    private static final Map<ResourcePackType, PackFinder> finders = new ConcurrentHashMap<>();

    private final ResourcePackType type;
    private final List<VirtualResourcepack> resourcePacks = new LinkedList<>();

    public PackFinder(ResourcePackType type) {
        this.type = type;
    }

    public void register(VirtualResourcepack pack) {
        resourcePacks.add(pack);
    }

    //todo not entirely sure if this is updated right
    @Override
    public void loadPacks(Consumer<ResourcePackInfo> map, ResourcePackInfo.IFactory factory) {
        Log.info("Adding virtual packs: {}", type);
        for (VirtualResourcepack pack : resourcePacks) {
            String name = pack.getName();
            boolean client = type == ResourcePackType.CLIENT_RESOURCES;
            Supplier<IResourcePack> supplier = () -> pack;
            PackMetadataSection metadata = new VirtualMeta(name, "").toMetadata();
            ResourcePackInfo.Priority priority = ResourcePackInfo.Priority.BOTTOM;
            ResourcePackInfo info = factory.create(name, client, supplier, pack, metadata, priority, IPackNameDecorator.DEFAULT);
            map.accept(info);
            Log.info("Added virtual pack: {}", name);
        }
    }

    public void register(IResourceManager resourceManager, Consumer<IPackFinder> packList) {
        Consumer<IResourcePack> consumer = pack -> {};
        if (resourceManager instanceof FallbackResourceManager) {
            consumer = ((FallbackResourceManager) resourceManager)::add;
        } else if (resourceManager instanceof SimpleReloadableResourceManager) {
            consumer = ((SimpleReloadableResourceManager) resourceManager)::add;
        }
        packList.accept(this);
        resourcePacks.forEach(consumer);
    }

    public static PackFinder getInstance(ResourcePackType type) {
        return finders.computeIfAbsent(type, PackFinder::new);
    }
}
