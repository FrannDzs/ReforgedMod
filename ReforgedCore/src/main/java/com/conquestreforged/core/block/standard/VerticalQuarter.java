package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.base.WaterloggedDirectionalShape;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShape;

@Assets(
        state = @State(name = "%s_vertical_quarter", template = "parent_vertical_quarter"),
        item = @Model(name = "item/%s_vertical_quarter", parent = "block/%s_vertical_quarter", template = "item/parent_vertical_quarter"),
        block = {
                @Model(name = "block/%s_vertical_quarter", template = "block/parent_vertical_quarter"),
        }
)
public class VerticalQuarter extends WaterloggedDirectionalShape {

    private static final VoxelShape NORTH_SHAPE = Block.makeCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape SOUTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 8.0D);
    private static final VoxelShape EAST_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 16.0D, 16.0D);
    private static final VoxelShape WEST_SHAPE = Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);

    public VerticalQuarter(Properties properties) {
        super(properties);
        this.setDefaultState((this.stateContainer.getBaseState()).with(DIRECTION, Direction.NORTH).with(WATERLOGGED, false));

    }

    @Override
    public VoxelShape getShape(BlockState state) {
        switch (state.get(DIRECTION)) {
            case NORTH:
            default:
                return NORTH_SHAPE;
            case SOUTH:
                return SOUTH_SHAPE;
            case WEST:
                return WEST_SHAPE;
            case EAST:
                return EAST_SHAPE;
        }
    }
}
