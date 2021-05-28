package com.conquestreforged.core.block.base;

import com.conquestreforged.core.block.properties.Waterloggable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;

/**
 * A non full-cube shape that can be 'waterlogged'
 */
public abstract class WaterloggedShape extends Shape implements Waterloggable {

    public WaterloggedShape(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());
        BlockState base = super.getStateForPlacement(context);
        if (base == null) {
            base = defaultBlockState();
        }
        return base.setValue(WATERLOGGED, fluid.getType() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected final void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
        addProperties(builder);
    }

    protected void addProperties(StateContainer.Builder<Block, BlockState> builder) {

    }
}
