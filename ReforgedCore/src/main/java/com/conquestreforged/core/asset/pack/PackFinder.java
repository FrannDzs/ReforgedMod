package com.conquestreforged.core.asset.pack;

import com.conquestreforged.core.asset.meta.VirtualMeta;
import com.conquestreforged.core.proxy.Proxies;
import com.conquestreforged.core.util.Log;
import net.minecraft.resources.IPackFinder;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.data.PackMetadataSection;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
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
        Log.info("Adding virtual pack: {}", type);
        for (VirtualResourcepack pack : resourcePacks) {
            String name = pack.getName();
            boolean client = type == ResourcePackType.CLIENT_RESOURCES;
            Supplier<IResourcePack> supplier = () -> pack;
            PackMetadataSection metadata = new VirtualMeta(name, "").toMetadata();
            ResourcePackInfo.Priority priority = ResourcePackInfo.Priority.BOTTOM;
            T info = factory.create(name, client, supplier, pack, metadata, priority);
            map.put(name, info);
        }
    }

    public void register() {
        Proxies.get(type).getResourcePackList().addPackFinder(this);
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
}
