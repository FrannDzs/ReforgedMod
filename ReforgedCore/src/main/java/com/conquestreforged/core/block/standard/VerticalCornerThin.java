package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.base.WaterloggedDirectionalShape;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

@Assets(
        state = @State(name = "%s_thin_vertical_corner", template = "parent_vertical_corner_thin"),
        item = @Model(name = "item/%s_thin_vertical_corner", parent = "block/%s_vertical_corner_thin", template = "item/parent_vertical_corner_thin"),
        block = {
                @Model(name = "block/%s_vertical_corner_thin", template = "block/parent_vertical_corner_thin"),
        }
)
public class VerticalCornerThin extends WaterloggedDirectionalShape {

    private static final VoxelShape EAST = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);
    private static final VoxelShape QTR_EAST = Block.makeCuboidShape(3.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape EAST_SHAPE = VoxelShapes.or(EAST, QTR_EAST);

    private static final VoxelShape WEST = Block.makeCuboidShape(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape QTR_WEST = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 13.0D, 16.0D, 3.0D);
    private static final VoxelShape WEST_SHAPE = VoxelShapes.or(WEST, QTR_WEST);

    private static final VoxelShape NORTH = Block.makeCuboidShape(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape QTR_NORTH = Block.makeCuboidShape(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 13.0D);
    private static final VoxelShape NORTH_SHAPE = VoxelShapes.or(NORTH, QTR_NORTH);

    private static final VoxelShape SOUTH = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
    private static final VoxelShape QTR_SOUTH = Block.makeCuboidShape(0.0D, 0.0D, 3.0D, 3.0D, 16.0D, 16.0D);
    private static final VoxelShape SOUTH_SHAPE = VoxelShapes.or(SOUTH, QTR_SOUTH);


    public VerticalCornerThin(Properties properties) {
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
