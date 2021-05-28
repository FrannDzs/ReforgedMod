package com.conquestreforged.core.data;

import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class DataResourceManager implements IResourceManager {

    private final ResourcePackType type;
    private final ExistingFileHelper helper;

    public DataResourceManager(ResourcePackType type, ExistingFileHelper helper) {
        this.type = type;
        this.helper = helper;
    }

    @Override
    public Set<String> getNamespaces() {
        return Collections.emptySet();
    }

    @Override
    public IResource getResource(ResourceLocation location) throws IOException {
        int pre = location.getPath().indexOf('/');
        int suf = location.getPath().lastIndexOf('.');
        String prefix = location.getPath().substring(0, pre);
        String suffix = location.getPath().substring(suf);
        String path = location.getPath().substring(pre + 1, suf);
        ResourceLocation name = new ResourceLocation(location.getNamespace(), path);
        return helper.getResource(name, type, suffix, prefix);
    }

    @Override
    public boolean hasResource(ResourceLocation location) {
        int pre = location.getPath().indexOf('/');
        int suf = location.getPath().lastIndexOf('.');
        String prefix = location.getPath().substring(0, pre);
        String suffix = location.getPath().substring(suf);
        String path = location.getPath().substring(pre + 1, suf);
        ResourceLocation name = new ResourceLocation(location.getNamespace(), path);
        return helper.exists(name, type, suffix, prefix);
    }

    @Override
    public List<IResource> getResources(ResourceLocation location) throws IOException {
        return Collections.emptyList();
    }

    @Override
    public Collection<ResourceLocation> listResources(String path, Predicate<String> filter) {
        return Collections.emptyList();
    }

    //todo unsure about this one
    @Override
    public Stream<IResourcePack> listPacks() {
        return Stream.of();
    }
}
