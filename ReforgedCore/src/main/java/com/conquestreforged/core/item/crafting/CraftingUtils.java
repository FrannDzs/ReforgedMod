package com.conquestreforged.core.item.crafting;

import com.conquestreforged.core.util.Log;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Optional;

public class CraftingUtils {

    public static Optional<? extends IRecipe> getRecipe(Block block) {
        ClientPlayNetHandler client = Minecraft.getInstance().getConnection();
        if (client == null) {
            return Optional.empty();
        }
        ResourceLocation name = block.getRegistryName();
        if (name == null) {
            return Optional.empty();
        }
        return client.getRecipeManager().getRecipe(name);
    }

    public static ItemStack getRecipeOutput(IInventory inventory, IRecipe recipe) {
        ItemStack out = recipe.getRecipeOutput();
        List<UniqueIngredient> required = UniqueIngredient.getIngredients(recipe);
        List<UniqueIngredient> available = UniqueIngredient.countStacks(inventory, required);
        Log.debug("{} r:{} a:{}", recipe.getId(), required.size(), available.size());
        if (!required.containsAll(available)) {
            out.setCount(0);
            return out;
        }

        int count = out.getMaxStackSize();
        for (UniqueIngredient req : required) {
            for (UniqueIngredient av : available) {
                if (!req.equals(av)) {
                    continue;
                }

                if (av.getCount() < req.getCount()) {
                    count = 0;
                    break;
                }

                count = Math.min(count, av.getCount() / req.getCount());
            }
        }
        out.setCount(count);
        return out;
    }
}
