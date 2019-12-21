package com.conquestreforged.core.proxy;

import com.conquestreforged.core.item.crafting.RecipeHelper;
import com.conquestreforged.core.util.OptionalValue;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.resources.*;

import java.util.function.Consumer;

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

    default Consumer<IResourcePack> getPackAdder() {
        IResourceManager manager = getResourceManager();
        if (manager instanceof FallbackResourceManager) {
            return ((FallbackResourceManager) manager)::addResourcePack;
        }
        if (manager instanceof SimpleReloadableResourceManager) {
            return ((SimpleReloadableResourceManager) manager)::addResourcePack;
        }
        return pack -> {};
    }

    default boolean isAbsent() {
        return false;
    }
}
