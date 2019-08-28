package com.conquestreforged.core.block.data;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class BlockDataRegistry {

    public static final List<BlockData> BLOCK_DATA = new LinkedList<>();

    public static Stream<String> getNamespaces() {
        return BLOCK_DATA.stream().map(data -> data.getRegistryName().getNamespace()).distinct();
    }

    public static Stream<BlockData> getData(String namespace) {
        return BLOCK_DATA.stream().filter(data -> data.getRegistryName().getNamespace().equals(namespace));
    }

    public static void registerBlock(BlockData block) {
        BLOCK_DATA.add(block);
    }

    public static void clear() {
        BLOCK_DATA.clear();
    }
}
