package com.conquestreforged.core.block.factory;

import com.conquestreforged.core.block.builder.BlockName;
import com.conquestreforged.core.block.builder.Props;
import com.conquestreforged.core.block.data.BlockData;
import com.conquestreforged.core.block.data.BlockDataRegistry;
import com.conquestreforged.core.init.Context;
import com.conquestreforged.core.item.family.FamilyRegistry;
import com.conquestreforged.core.item.family.block.BlockFamily;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;

public interface BlockFactory {

    Props getProps();

    BlockName getName();

    BlockState getParent();

    default void register(TypeList types) {
        BlockFamily family = new BlockFamily(getProps().group(), types);
        for (Class<? extends Block> type : types) {
            BlockType blockType = BlockTypeCache.getInstance().get(type);
            BlockData data = register(blockType);
            family.add(data.getBlock());
        }
        FamilyRegistry.BLOCKS.register(family);
    }

    default BlockData register(String name, BlockType type) throws InitializationException {
        return register(new ResourceLocation(Context.getInstance().getNamespace(), name), type);
    }

    default BlockData register(ResourceLocation name, BlockType type) throws InitializationException {
        BlockName blockName = BlockName.of(name.getNamespace(), name.getPath(), name.getPath());
        Props props = getProps();
        Block block = type.create(getProps());
        return register(block, blockName, props);
    }

    default BlockData register(BlockType type) throws InitializationException {
        BlockName name = getName();
        Props props = getProps();
        Block block = type.create(props);
        return register(block, name, props);
    }

    default BlockData register(Block block, BlockName name, Props props) {
        BlockData data = new BlockData(block, name, props);
        BlockDataRegistry.registerBlock(data);
        if (!getProps().hasParent()) {
            getProps().parent(data.getBlock().getDefaultState());
        }
        return data;
    }
}
