package com.conquestreforged.core.asset;

import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.ResourcePackType;

import java.io.IOException;
import java.io.InputStream;

public interface VirtualResource {

    String getPath();

    String getNamespace();

    ResourcePackType getType();

    InputStream getInputStream(IResourceManager resourceManager) throws IOException;
}
