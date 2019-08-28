package com.conquestreforged.core.proxy.impl;

import com.conquestreforged.core.proxy.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.ResourcePackList;

public class ClientProxy extends AbstractProxy {

    public ClientProxy() {
        super(Side.CLIENT);
    }

    @Override
    public IResourceManager getResourceManager() {
        return Minecraft.getInstance().getResourceManager();
    }

    @Override
    public ResourcePackList getResourcePackList() {
        return Minecraft.getInstance().getResourcePackList();
    }

    @Override
    public RecipeManager getRecipeManager() {
        ClientPlayNetHandler client = Minecraft.getInstance().getConnection();
        if (client == null) {
            return new RecipeManager();
        }
        return client.getRecipeManager();
    }
}
