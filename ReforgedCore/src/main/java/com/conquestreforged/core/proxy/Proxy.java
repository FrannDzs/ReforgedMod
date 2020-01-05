package com.conquestreforged.core.proxy;

import com.conquestreforged.core.item.crafting.RecipeHelper;
import com.conquestreforged.core.util.OptionalValue;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.resources.*;

public interface Proxy extends OptionalValue {

    IResourceManager getResourceManager();

    ResourcePackList getResourcePackList();

    RecipeManager getRecipeManager();

    RecipeHelper getRecipes();

    default void register(IReloadableResourceManager manager) {

    }

    default Proxy registerListeners() {
        if (isAbsent()) {
            return this;
        }
        IResourceManager manager = getResourceManager();
        if (manager instanceof IReloadableResourceManager) {
            IReloadableResourceManager reloadable = (IReloadableResourceManager) manager;
            register(reloadable);
        }
        return this;
    }

    default void registerResourcePack(IResourcePack pack) {
        IResourceManager manager = getResourceManager();
        if (manager instanceof FallbackResourceManager) {
            ((FallbackResourceManager) manager).addResourcePack(pack);
            return;
        }
        if (manager instanceof SimpleReloadableResourceManager) {
            ((SimpleReloadableResourceManager) manager).addResourcePack(pack);
        }
    }

    default boolean isAbsent() {
        return false;
    }
}
