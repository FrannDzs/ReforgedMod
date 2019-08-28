package com.conquestreforged.core.proxy;

import com.conquestreforged.core.item.crafting.RecipeHelper;
import com.conquestreforged.core.util.OptionalValue;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.ResourcePackList;

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

    default boolean isAbsent() {
        return false;
    }
}
