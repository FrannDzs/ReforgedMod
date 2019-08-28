package com.conquestreforged.core.proxy.impl;

import com.conquestreforged.core.proxy.Side;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.server.MinecraftServer;

public class ServerProxy extends AbstractProxy {

    private final MinecraftServer server;

    public ServerProxy(MinecraftServer server) {
        super(Side.SERVER);
        this.server = server;
    }

    @Override
    public IResourceManager getResourceManager() {
        return server.getResourceManager();
    }

    @Override
    public ResourcePackList getResourcePackList() {
        return server.getResourcePacks();
    }

    @Override
    public RecipeManager getRecipeManager() {
        return server.getRecipeManager();
    }
}
