package com.conquestreforged.core.item.crafting;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;

import java.util.*;

public class UniqueIngredient {

    private final Set<UniqueStack> stacks;
    private int count = 0;

    private UniqueIngredient(ItemStack[] stacks) {
        this.stacks = new HashSet<>();
        for (ItemStack stack : stacks) {
            this.stacks.add(new UniqueStack(stack));
        }
    }

    private UniqueIngredient(Set<UniqueStack> stacks) {
        this.stacks = stacks;
    }

    public int getCount() {
        return count;
    }

    public boolean matches(ItemStack stack) {
        return matches(new UniqueStack(stack));
    }

    public boolean matches(UniqueStack stack) {
        return stacks.contains(stack);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniqueIngredient that = (UniqueIngredient) o;
        return stacks.equals(that.stacks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stacks);
    }

    public static List<UniqueIngredient> getIngredients(IRecipe<?> recipe) {
        Map<Object, UniqueIngredient> map = new HashMap<>();
        for (Ingredient ingredient : recipe.getIngredients()) {
            UniqueIngredient i = new UniqueIngredient(ingredient.getMatchingStacks());
            map.computeIfAbsent(i, o -> i).count++;
        }
        return new ArrayList<>(map.values());
    }

    public static List<UniqueIngredient> countStacks(IInventory inventory, List<UniqueIngredient> ingredients) {
        Map<UniqueIngredient, UniqueIngredient> map = new HashMap<>();
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            for (UniqueIngredient ingredient : ingredients) {
                if (ingredient.matches(stack)) {
                    map.computeIfAbsent(ingredient, in -> new UniqueIngredient(in.stacks)).count++;
                    break;
                }
            }
        }
        return new ArrayList<>(map.values());
    }
}
