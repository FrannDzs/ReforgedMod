package com.conquestreforged.core.asset.pack;

import com.conquestreforged.core.asset.meta.VirtualMeta;
import com.conquestreforged.core.util.Log;
import net.minecraft.resources.*;
import net.minecraft.resources.data.PackMetadataSection;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
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

    @Override
    public <T extends ResourcePackInfo> void addPackInfosToMap(Map<String, T> map, ResourcePackInfo.IFactory<T> factory) {
        Log.info("Adding virtual packs: {}", type);
        for (VirtualResourcepack pack : resourcePacks) {
            String name = pack.getName();
            boolean client = type == ResourcePackType.CLIENT_RESOURCES;
            Supplier<IResourcePack> supplier = () -> pack;
            PackMetadataSection metadata = new VirtualMeta(name, "").toMetadata();
            ResourcePackInfo.Priority priority = ResourcePackInfo.Priority.BOTTOM;
            T info = factory.create(name, client, supplier, pack, metadata, priority);
            map.put(name, info);
            Log.info("Added virtual pack: {}", name);
        }
    }

    public void register(IResourceManager resourceManager, ResourcePackList<?> packList) {
        Consumer<IResourcePack> consumer = pack -> {};
        if (resourceManager instanceof FallbackResourceManager) {
            consumer = ((FallbackResourceManager) resourceManager)::addResourcePack;
        } else if (resourceManager instanceof SimpleReloadableResourceManager) {
            consumer = ((SimpleReloadableResourceManager) resourceManager)::addResourcePack;
        }
        packList.addPackFinder(this);
        resourcePacks.forEach(consumer);
    }

    public static PackFinder getInstance(ResourcePackType type) {
        return finders.computeIfAbsent(type, PackFinder::new);
    }

    public static void iterate(BiConsumer<ResourcePackType, VirtualResourcepack> consumer) {
        for (PackFinder finder : finders.values()) {
            for (VirtualResourcepack pack : finder.resourcePacks) {
                consumer.accept(finder.type, pack);
            }
        }
    }

    public static void export(File dir, boolean pretty) {
        iterate((type, pack) -> {
            try {
                if (pretty) {
                    pack.exportPretty(dir);
                } else {
                    pack.export(dir);
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
