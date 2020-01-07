package com.conquestreforged.client.palette;

import com.conquestreforged.client.palette.palette2.PaletteScreen2;
import com.conquestreforged.client.texture.ItemGroup;
import com.conquestreforged.client.texture.TextureGroup;
import com.conquestreforged.client.texture.TextureGroupManager;
import com.conquestreforged.core.client.input.BindEvent;
import com.conquestreforged.core.client.input.BindListener;
import com.conquestreforged.core.item.family.Family;
import com.conquestreforged.core.item.family.FamilyRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PaletteBindListener implements BindListener {

    @Override
    public void onPress(BindEvent e) {
        if (!e.inGame || !e.player.isPresent()) {
            return;
        }

        e.player.map(PlayerEntity::getHeldItemMainhand).ifPresent(stack -> {
            List<ItemStack> palette = create(stack.getItem());
            PaletteScreen2 screen = PaletteScreen2.create(e.player.get(), stack, palette);
            Minecraft.getInstance().displayGuiScreen(screen);
        });
    }

    private static List<ItemStack> create(Item item) {
        if (item instanceof BlockItem) {
            return create(((BlockItem) item).getBlock());
        }
        return Collections.emptyList();
    }

    private static List<ItemStack> create(Block block) {
        if (block == Blocks.AIR) {
            return Collections.emptyList();
        }

        Family<Block> family = FamilyRegistry.BLOCKS.getFamily(block);
        if (family.isAbsent()) {
            return Collections.emptyList();
        }

        NonNullList<ItemStack> items = NonNullList.create();
        family.addAllItems(family.getGroup(), items);
        return items;
    }
}
