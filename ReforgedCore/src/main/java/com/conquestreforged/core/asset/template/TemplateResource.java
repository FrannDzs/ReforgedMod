package com.conquestreforged.core.asset.template;

import com.conquestreforged.core.asset.VirtualResource;
import com.conquestreforged.core.proxy.Proxies;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.ResourcePackType;

import java.io.IOException;
import java.io.InputStream;

public class TemplateResource implements VirtualResource {

    private final String path;
    private final String namespace;
    private final JsonTemplate template;
    private final JsonOverride overrides;
    private final ResourcePackType packType;

    public TemplateResource(ResourcePackType type, String namespace, String path, JsonOverride overrides, JsonTemplate template) {
        this.path = path;
        this.namespace = namespace;
        this.overrides = overrides;
        this.template = template;
        this.packType = type;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getNamespace() {
        return namespace;
    }

    @Override
    public ResourcePackType getType() {
        return packType;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        IResourceManager manager = Proxies.get(getType()).getResourceManager();
        return template.getInputStream(manager, overrides);
    }

    @Override
    public String toString() {
        return "TemplateResource{" +
                "path=" + path +
                ", template=" + template +
                '}';
    }
}
