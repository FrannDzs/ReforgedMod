package com.conquestreforged.client.gui.dependency;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.ClientResourcePackInfo;
import net.minecraftforge.fml.ModList;

import java.io.IOException;

public enum DependencyType {
    UNKNOWN {
        @Override
        public boolean isAvailable(Dependency dependency) {
            return true;
        }
    },
    MOD {
        @Override
        public boolean isAvailable(Dependency dependency) {
            return ModList.get().isLoaded(dependency.getId());
        }
    },
    RESOURCEPACK {
        @Override
        public boolean isAvailable(Dependency dependency) {
            for (ClientResourcePackInfo pack : Minecraft.getInstance().getResourcePackList().getEnabledPacks()) {
                try {
                    String packId = pack.getResourcePack().getMetadata(PackIdDeserializer.INSTANCE);
                    if (packId != null && packId.equalsIgnoreCase(dependency.getId())) {
                        return true;
                    }
                } catch (IOException ignored) {

                }
            }
            return false;
        }
    },
    ;

    public abstract boolean isAvailable(Dependency dependency);

    static DependencyType of(String name) {
        for (DependencyType type : DependencyType.values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
