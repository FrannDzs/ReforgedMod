package com.conquestreforged.core.asset.lang;

import com.conquestreforged.core.util.Translatable;
import com.conquestreforged.core.util.cache.Cache;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Translations extends Cache<String, String> {

    private static final Translations instance = new Translations();

    private Translations() {

    }

    public void add(Translatable translatable) {
        put(translatable.getTranslationKey(), translatable.getDisplayName());
    }

    public void add(String type, IForgeRegistryEntry<?> entry, String translation) {
        String key = type + "." + entry.getRegistryName().getNamespace() + "." + entry.getRegistryName().getPath();
        put(key, translation);
    }

    public void add(String type, ResourceLocation name, String translation) {
        String key = type + "." + name.getNamespace() + "." + name.getPath();
        put(key, translation);
    }

    @Override
    public String compute(String s) {
        return "";
    }

    public static Translations getInstance() {
        return instance;
    }
}
