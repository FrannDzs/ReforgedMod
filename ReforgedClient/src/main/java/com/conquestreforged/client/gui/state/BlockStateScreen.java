package com.conquestreforged.client.gui.state;

import com.conquestreforged.client.gui.PickerScreen;
import com.conquestreforged.client.gui.render.Render;
import com.conquestreforged.core.item.ItemUtils;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IProperty;
import net.minecraft.state.properties.BlockStateProperties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public class BlockStateScreen extends PickerScreen<BlockState> {

    private final Collection<IProperty<?>> properties;

    public BlockStateScreen(ItemStack stack, BlockState state, List<BlockState> states, Collection<IProperty<?>> properties) {
        super("State Selector", stack, state, filterWaterlogged(state, states));
        this.properties = properties;
    }

    @Override
    public int getSize() {
        return 3;
    }

    @Override
    public int getWidth(BlockState option) {
        return 8;
    }

    @Override
    public int getHeight(BlockState option) {
        return 8;
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
        ItemStack stack = ItemUtils.fromState(value, properties);
        stack.setCount(original.getCount());
        return stack;
    }

    private static List<BlockState> filterWaterlogged(BlockState state, List<BlockState> in) {
        if (state.has(BlockStateProperties.WATERLOGGED)) {
            List<BlockState> out = new ArrayList<>(in.size() / 2);
            for (BlockState s : in) {
                if (!s.get(BlockStateProperties.WATERLOGGED)) {
                    out.add(s);
                }
            }
            return out;
        }
        return in;
    }

    /**
     * @param stack the current ItemStack
     * @param state the BLockState derrived from the provided ItemStack
     * @param filter a filter of IProperties to ignore (true == ignore!)
     */
    public static Optional<BlockStateScreen> of(ItemStack stack, BlockState state, Predicate<IProperty<?>> filter) {
        Map<IProperty<?>, Object> defaults = new HashMap<>();
        Set<IProperty<?>> properties = new HashSet<>();

        for (IProperty<?> property : state.getProperties()) {
            if (filter.test(property)) {
                Object defValue = state.get(property);
                defaults.put(property, defValue);
            } else {
                properties.add(property);
            }
        }

        List<BlockState> states = new ArrayList<>();
        for (BlockState candidate : state.getBlock().getStateContainer().getValidStates()) {
            boolean valid = true;
            for (Map.Entry<IProperty<?>, Comparable<?>> e : candidate.getValues().entrySet()) {
                Object defValue = defaults.get(e.getKey());
                if (defValue != null && !defValue.equals(e.getValue())) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                states.add(candidate);
            }
        }

        if (states.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new BlockStateScreen(stack, state, states, properties));
    }
}
