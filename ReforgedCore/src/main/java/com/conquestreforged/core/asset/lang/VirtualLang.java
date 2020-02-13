package com.conquestreforged.core.asset.lang;

import com.conquestreforged.core.asset.VirtualResource;
import com.conquestreforged.core.util.ByteStream;
import com.google.gson.JsonElement;
import com.google.gson.internal.bind.JsonTreeWriter;
import com.google.gson.stream.JsonWriter;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;

public class VirtualLang implements VirtualResource {

    private final String path;
    private final String namespace;

    public VirtualLang(String namespace) {
        this.namespace = namespace;
        this.path = "assets/" + namespace + "/lang/en_us.json";
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
        return ResourcePackType.CLIENT_RESOURCES;
    }

    @Override
    public JsonElement getJson(IResourceManager resourceManager) throws IOException {
        JsonTreeWriter writer = new JsonTreeWriter();
        write(writer);
        return writer.get();
    }

    @Override
    public InputStream getInputStream(IResourceManager resourceManager) throws IOException {
        ByteStream.Output output = new ByteStream.Output();
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(output));
        write(writer);
        writer.flush();
        return output.toInputStream();
    }

    private void write(JsonWriter writer) throws IOException {
        writer.setIndent("  ");
        writer.beginObject();
        Set<String> visited = new HashSet<>();
        // give specific translations priority
        Translations.getInstance().forEach((key, value) -> writeSafe(key, value, writer, visited));
        writeTranslations(ForgeRegistries.BLOCKS, "block", writer, visited);
        writeTranslations(ForgeRegistries.ITEMS, "item", writer, visited);
        writeTranslations(ForgeRegistries.ENTITIES, "entity", writer, visited);
        writer.endObject();
    }

    private void writeSafe(String key, String value, JsonWriter writer, Set<String> visited) {
        if (visited.add(key)) {
            try {
                writer.name(key).value(value);
            } catch (IOException ignored) {

            }
        }
    }

    private void writeTranslations(IForgeRegistry<?> registry, String type, JsonWriter writer, Set<String> visited) throws IOException {
        for (IForgeRegistryEntry<?> entry : registry) {
            ResourceLocation name = entry.getRegistryName();
            if (name == null || !name.getNamespace().equals(getNamespace())) {
                continue;
            }

            String key = type + '.' + name.getNamespace() + '.' + name.getPath();
            if (!visited.add(key)) {
                continue;
            }

            String value = translate(name.getPath());
            writer.name(key);
            writer.value(value);
        }
    }

    private static String translate(String in) {
        char[] out = new char[in.length()];
        boolean first = true;
        for (int i = 0; i < in.length(); i++) {
            char c = in.charAt(i);
            if (first) {
                first = false;
                c = Character.toUpperCase(c);
            } else if (c == '_') {
                c = ' ';
                first = true;
            }
            out[i] = c;
        }
        return new String(out);
    }
}
