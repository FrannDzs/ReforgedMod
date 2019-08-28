package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.*;
import com.conquestreforged.core.block.base.WaterloggedDirectionalShape;
import com.conquestreforged.core.block.properties.Waterloggable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

@Assets(
        state = @State(name = "%s_arrowslit", template = "parent_arrowslit"),
        item = @Model(name = "item/%s_arrowslit", parent = "block/%s_arrowslit", template = "item/parent_arrowslit"),
        block = {
                @Model(name = "block/%s_arrowslit", template = "block/parent_arrowslit"),
        },
        recipe = @Recipe(
                name = "%s_slab",
                template = "acacia_slab",
                ingredients = {
                        @Ingredient(name = "%s", template = "acacia_planks", plural = true)
                }
        )
)
public class ArrowSlit extends WaterloggedDirectionalShape implements Waterloggable {

    private static final VoxelShape EAST_FR = Block.makeCuboidShape(0.0D, 0.0D, 9.0D, 1.0D, 16.0D, 13.0D);
    private static final VoxelShape EAST_FL = Block.makeCuboidShape(0.0D, 0.0D, 3.0D, 1.0D, 16.0D, 7.0D);
    private static final VoxelShape EAST_SR = Block.makeCuboidShape(0.0D, 0.0D, 13.0D, 8.0D, 16.0D, 16.0D);
    private static final VoxelShape EAST_SL = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 3.0D);
    private static final VoxelShape EAST_SHAPE = VoxelShapes.or(VoxelShapes.or(EAST_FR, EAST_FL), VoxelShapes.or(EAST_SR, EAST_SL));

    private static final VoxelShape WEST_FR = Block.makeCuboidShape(16.0D, 0.0D, 9.0D, 15.0D, 16.0D, 13.0D);
    private static final VoxelShape WEST_FL = Block.makeCuboidShape(16.0D, 0.0D, 3.0D, 15.0D, 16.0D, 7.0D);
    private static final VoxelShape WEST_SR = Block.makeCuboidShape(16.0D, 0.0D, 13.0D, 8.0D, 16.0D, 16.0D);
    private static final VoxelShape WEST_SL = Block.makeCuboidShape(16.0D, 0.0D, 0.0D, 8.0D, 16.0D, 3.0D);
    private static final VoxelShape WEST_SHAPE = VoxelShapes.or(VoxelShapes.or(WEST_FR, WEST_FL), VoxelShapes.or(WEST_SR, WEST_SL));

    private static final VoxelShape NORTH_FR = Block.makeCuboidShape(9.0D, 0.0D, 15.0D, 13.0D, 16.0D, 16.0D);
    private static final VoxelShape NORTH_FL = Block.makeCuboidShape(3.0D, 0.0D, 15.0D, 7.0D, 16.0D, 16.0D);
    private static final VoxelShape NORTH_SR = Block.makeCuboidShape(13.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape NORTH_SL = Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 3.0D, 16.0D, 16.0D);
    private static final VoxelShape NORTH_SHAPE = VoxelShapes.or(VoxelShapes.or(NORTH_FR, NORTH_FL), VoxelShapes.or(NORTH_SR, NORTH_SL));

    private static final VoxelShape SOUTH_FR = Block.makeCuboidShape(9.0D, 0.0D, 1.0D, 13.0D, 16.0D, 0.0D);
    private static final VoxelShape SOUTH_FL = Block.makeCuboidShape(3.0D, 0.0D, 1.0D, 7.0D, 16.0D, 0.0D);
    private static final VoxelShape SOUTH_SR = Block.makeCuboidShape(13.0D, 0.0D, 8.0D, 16.0D, 16.0D, 0.0D);
    private static final VoxelShape SOUTH_SL = Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 3.0D, 16.0D, 0.0D);
    private static final VoxelShape SOUTH_SHAPE = VoxelShapes.or(VoxelShapes.or(SOUTH_FR, SOUTH_FL), VoxelShapes.or(SOUTH_SR, SOUTH_SL));

    public ArrowSlit(Properties properties) {
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
