package com.conquestreforged.client.gui.dependency;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.ClientResourcePackInfo;
import net.minecraftforge.fml.ModList;

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
                String packName = pack.getName().replaceAll("[^a-zA-Z0-9_]", "").toLowerCase();
                if (packName.contains(dependency.getId())) {
                    return true;
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
