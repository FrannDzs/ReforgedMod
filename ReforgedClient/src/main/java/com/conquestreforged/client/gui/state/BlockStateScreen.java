package com.conquestreforged.client.gui.state;

import com.conquestreforged.client.gui.PickerScreen;
import com.conquestreforged.client.gui.render.Render;
import com.conquestreforged.core.item.ItemUtils;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;

public class BlockStateScreen extends PickerScreen<BlockState> {

    public BlockStateScreen(ItemStack stack, BlockState state) {
        super("State Selector", stack, state, state.getBlock().getStateContainer().getValidStates());
    }

    @Override
    public int getWidth(BlockState option) {
        return 0;
    }

    @Override
    public int getHeight(BlockState option) {
        return 0;
    }

    @Override
    public String getDisplayName(BlockState option) {
        StringBuilder sb = new StringBuilder();
        option.getProperties().forEach(p -> sb.append(sb.length() > 0 ? "," : "").append(p.getName()).append('=').append(option.get(p)));
        return sb.toString();
    }

    @Override
    public void render(BlockState option, int x, int y, int width, int height) {
        Render.drawBlockModel(option, x, y, 1F);
    }

    @Override
    public ItemStack createItemStack(ItemStack original, BlockState value) {
        ItemStack stack = ItemUtils.fromState(value);
        stack.setCount(original.getCount());
        return stack;
    }
}
