package com.conquestreforged.core.item.crafting;

import com.conquestreforged.core.proxy.Side;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import java.util.*;

public class RecipeHelper implements IResourceManagerReloadListener {

    private final Side side;
    private final Map<Stack, IRecipe> recipes = new HashMap<>();

    public RecipeHelper(Side side) {
        this.side = side;
    }

    private void init() {
        for (IRecipe recipe : side.getProxy().getRecipeManager().getRecipes()) {
            ItemStack result = recipe.getRecipeOutput();
            Stack stack = new Stack(result);
            recipes.put(stack, recipe);
        }
    }

    public Optional<? extends IRecipe> getRecipe(ResourceLocation name) {
        return side.getProxy().getRecipeManager().getRecipe(name);
    }

    public Optional<IRecipe> getRecipe(ItemStack stack) {
        return Optional.ofNullable(recipes.get(new Stack(stack)));
    }

    public Result destruct(ItemStack stack, float exchangeRate) {
        Optional<IRecipe> optionalRecipe = getRecipe(stack);
        if (!optionalRecipe.isPresent()) {
            return Result.EMPTY;
        }

        IRecipe<?> recipe = optionalRecipe.get();
        int size = recipe.getRecipeOutput().getCount();
        if (stack.getCount() < size) {
            return new Result(Collections.singletonList(stack), Collections.emptyList());
        }

        int multiplier = stack.getCount() / size;
        int remainder = stack.getCount() % size;
        List<ItemStack> remaining = Collections.emptyList();
        if (remainder > 0) {
            ItemStack rem = stack.copy();
            rem.setCount(remainder);
            remaining = Collections.singletonList(rem);
        }

        Map<Stack, Stack> results = new HashMap<>();
        recipe.getIngredients().stream()
                .filter(i -> i != Ingredient.EMPTY)
                .filter(i -> i.getMatchingStacks().length > 0)
                .map(i -> i.getMatchingStacks()[0].copy())
                .filter(s -> !s.isEmpty())
                .forEach(s -> results.computeIfAbsent(new Stack(s), t -> t).inc(s.getCount()));

        List<ItemStack> converted = new ArrayList<>();
        results.values()
                .stream()
                .peek(s -> s.mult(multiplier * exchangeRate))
                .forEach(s -> s.drain(converted));

        return new Result(remaining, converted);
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
        if (side.getProxy().isAbsent()) {
            return;
        }
        recipes.clear();
        init();
    }

    private static ItemStack copyStack(ItemStack stack, int multiplier, float exchangeRate) {
        int count = MathHelper.ceil(stack.getCount() * multiplier * exchangeRate);
        ItemStack result = stack.copy();
        result.setCount(count);
        return result;
    }
}
