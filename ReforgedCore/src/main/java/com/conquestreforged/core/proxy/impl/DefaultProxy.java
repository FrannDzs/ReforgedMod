package com.conquestreforged.core.proxy.impl;

import com.conquestreforged.core.block.factory.InitializationException;
import com.conquestreforged.core.item.crafting.RecipeHelper;
import com.conquestreforged.core.proxy.Proxy;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.ResourcePackList;

public class DefaultProxy implements Proxy {

    public static final DefaultProxy INSTANCE = new DefaultProxy();

    @Override
    public IResourceManager getResourceManager() {
        throw new InitializationException("");
    }

    @Override
    public ResourcePackList getResourcePackList() {
        throw new InitializationException("");
    }

    @Override
    public RecipeManager getRecipeManager() {
        throw new InitializationException("");
    }

    @Override
    public RecipeHelper getRecipes() {
        throw new InitializationException("");
    }

    @Override
    public boolean isAbsent() {
        return true;
    }
}
