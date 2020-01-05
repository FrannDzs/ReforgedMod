package com.conquestreforged.core.asset.meta;

import com.conquestreforged.core.asset.VirtualResource;
import com.conquestreforged.core.util.ByteStream;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.data.PackMetadataSection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

public class VirtualMeta implements VirtualResource {

    private final String description;
    private final String namespace;

    public VirtualMeta(String namespace, String description) {
        this.description = description;
        this.namespace = namespace;
    }

    public JsonObject toJson() {
        JsonObject pack = new JsonObject();
        pack.addProperty("description", description);
        pack.addProperty("pack_format", 5);

        JsonObject meta = new JsonObject();
        meta.add("pack", pack);
        meta.add("language", new JsonObject());
        return pack;
    }

    public PackMetadataSection toMetadata() {
        return PackMetadataSection.SERIALIZER.deserialize(toJson());
    }

    @Override
    public String getPath() {
        return "pack.mcmeta";
    }

    @Override
    public String getNamespace() {
        return namespace;
    }

    @Override
    public ResourcePackType getType() {
        return ResourcePackType.CLIENT_RESOURCES;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        ByteStream.Output out = new ByteStream.Output();
        try (JsonWriter writer = new JsonWriter(new OutputStreamWriter(out))) {
            Streams.write(toJson(), writer);
        }
        return out.toInputStream();
    }
}
