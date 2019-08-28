package com.conquestreforged.core.proxy.impl;

import com.conquestreforged.core.item.crafting.RecipeHelper;
import com.conquestreforged.core.proxy.Proxy;
import com.conquestreforged.core.proxy.Side;
import net.minecraft.resources.IReloadableResourceManager;

public abstract class AbstractProxy implements Proxy {

    private final RecipeHelper recipeHelper;

    protected AbstractProxy(Side side) {
        recipeHelper = new RecipeHelper(side);
    }

    @Override
    public void register(IReloadableResourceManager manager) {
        //addReloadListener
        manager.addReloadListener(recipeHelper);
    }

    @Override
    public RecipeHelper getRecipes() {
        return recipeHelper;
    }
}
