package com.conquestreforged.core.util;

import com.conquestreforged.core.block.factory.InitializationException;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IProperty;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class State {

    public static BlockState parse(String input) {
        int domainEnd = input.indexOf(':');
        String domain = domainEnd == -1 ? "minecraft" : input.substring(0, domainEnd);

        int nameEnd = input.indexOf('[');
        String name = nameEnd == -1 ? input.substring(domainEnd + 1) : input.substring(domainEnd + 1, nameEnd);

        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(domain, name));
        if (block == null) {
            throw new InitializationException("invalid block " + input);
        }

        BlockState state = block.getDefaultState();
        for (int i = nameEnd + 1; i < input.length(); i++) {
            int keyStart = i;
            int keyEnd = indexOf(input, keyStart, '=');
            if (keyEnd == -1) {
                break;
            }

            int valStart = keyEnd + 1;
            int valEnd = indexOf(input, valStart, ',', ']');
            if (valEnd == -1) {
                break;
            }

            String key = input.substring(keyStart, keyEnd);
            String value = input.substring(valStart, valEnd);
            state = with(state, key, value);
            i = valEnd + 1;
        }

        return state;
    }

    private static int indexOf(String string, int from, char... chars) {
        for (char c : chars) {
            int i = string.indexOf(c, from);
            if (i != -1) {
                return i;
            }
        }
        return -1;
    }

    private static BlockState with(BlockState state, String key, String value) {
        IProperty<? extends Comparable> property = state.getBlock().getStateContainer().getProperty(key);
        if (property == null) {
            return state;
        }
        return with(state, property, value);
    }

    public static <T extends Comparable<T>> BlockState with(BlockState state, IProperty<T> property, String value) {
        return property.parseValue(value).map(t -> state.with(property, t)).orElse(state);
    }
}
