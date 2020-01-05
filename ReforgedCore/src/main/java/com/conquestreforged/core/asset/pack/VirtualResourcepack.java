package com.conquestreforged.core.asset.pack;

import com.conquestreforged.core.asset.VirtualResource;
import com.conquestreforged.core.asset.meta.VirtualMeta;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minecraft.resources.ResourcePack;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class VirtualResourcepack extends ResourcePack {

    private final Map<String, VirtualResource> resources;

    private VirtualResourcepack(ResourcePackType type, String name, Map<String, VirtualResource> resources) {
        super(new File(name));
        this.resources = resources;
        PackFinder.getInstance(type).register(this);
    }

    public boolean isEmpty() {
        return resources.isEmpty();
    }

    public VirtualResource getResource(ResourceLocation location) throws FileNotFoundException {
        return getResource(Locations.path(location));
    }

    public VirtualResource getResource(String resourcePath) throws FileNotFoundException {
        VirtualResource resource = resources.get(resourcePath);
        if (resource == null) {
            throw new FileNotFoundException(resourcePath);
        }
        return resource;
    }

    @Override
    protected InputStream getInputStream(String resourcePath) throws IOException {
        VirtualResource resource = resources.get(resourcePath);
        if (resource == null) {
            throw new FileNotFoundException(resourcePath);
        }
        return resource.getInputStream();
    }

    @Override
    protected boolean resourceExists(String resourcePath) {
        return resources.containsKey(resourcePath);
    }

    @Override
    // getAllResourceLocations
    public Collection<ResourceLocation> func_225637_a_(ResourcePackType type, String namespace, String path, int maxDepth, Predicate<String> filter) {
        String prefix = type.getDirectoryName() + "/" + namespace + "/";

        return resources.keySet().stream()
                .filter(s -> s.startsWith(prefix))
                .map(s -> {
                    String s1 = s.substring(prefix.length());
                    int i = s1.indexOf('/');
                    if (i >= 0) {
                        String s2 = s1.substring(i + 1);
                        if (s2.startsWith(path + "/")) {
                            String[] astring = s2.substring(path.length() + 2).split("/");
                            if (astring.length >= maxDepth + 1 && filter.test(s2)) {
                                String s3 = s1.substring(0, i);
                                return new ResourceLocation(s3, s2);
                            }
                        }
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public Set<String> getResourceNamespaces(ResourcePackType type) {
        return resources.values().stream()
                .filter(r -> r.getType() == type)
                .map(VirtualResource::getNamespace)
                .collect(Collectors.toSet());
    }

    @Override
    public void close() throws IOException {

    }

    public void export(File dir) throws IOException {
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException();
        }

        for (Map.Entry<String, VirtualResource> entry : resources.entrySet()) {
            File out = new File(dir, entry.getKey());
            File parent = out.getParentFile();
            if (!parent.exists() && !parent.mkdirs()) {
                throw new IOException();
            }

            try (InputStream inputStream = entry.getValue().getInputStream()) {
                if (inputStream == null) {
                    throw new IOException();
                }
                Files.copy(inputStream, out.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    public void exportPretty(File dir) throws IOException {
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Could not create directory: " + dir);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        JsonParser parser = new JsonParser();
        for (Map.Entry<String, VirtualResource> entry : resources.entrySet()) {
            File out = new File(dir, entry.getKey());
            File parent = out.getParentFile();
            if (!parent.exists() && !parent.mkdirs()) {
                throw new IOException();
            }

            try (InputStream inputStream = entry.getValue().getInputStream()) {
                if (inputStream == null) {
                    throw new IOException();
                }
                JsonElement element = parser.parse(new InputStreamReader(inputStream));
                try (FileWriter writer = new FileWriter(out)) {
                    gson.toJson(element, writer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Builder builder(String name) {
        return new Builder(name);
    }

    public static class Builder {

        private final String namespace;
        private final List<VirtualResource> resources = new LinkedList<>();
        private ResourcePackType type = ResourcePackType.CLIENT_RESOURCES;

        private Builder(String namespace) {
            this.namespace = namespace;
        }

        public Builder type(ResourcePackType type) {
            this.type = type;
            return this;
        }

        public Builder add(VirtualResource resource) {
            resources.add(resource);
            return this;
        }

        public VirtualResourcepack build() {
            Map<String, VirtualResource> map = new HashMap<>();
            // first so can be overridden
            map.put("pack.mcmeta", new VirtualMeta(namespace, namespace));
            // add resources second
            resources.forEach(r -> map.put(r.getPath(), r));

            String suffix = type == ResourcePackType.CLIENT_RESOURCES ? "_virtual_assets" : "_virtual_data";
            return new VirtualResourcepack(type, namespace + suffix, map);
        }
    }
}
