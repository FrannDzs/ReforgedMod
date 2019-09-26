package com.conquestreforged.core.block;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PaneBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

@Assets(
        state = @State(name = "%s", template = "parent_pane"),
        item = @Model(name = "item/%s", parent = "block/%s_pane_ns", template = "item/parent_pane"),
        block = {
                @Model(name = "block/%s_pane_n", template = "block/parent_flatpane_n"),
                @Model(name = "block/%s_pane_ne", template = "block/parent_flatpane_ne"),
                @Model(name = "block/%s_pane_ns", template = "block/parent_flatpane_ns"),
                @Model(name = "block/%s_pane_nse", template = "block/parent_flatpane_nse"),
                @Model(name = "block/%s_pane_nsew", template = "block/parent_flatpane_nsew"),
                @Model(name = "block/%s_pane_post", template = "block/parent_flatpane_post")
        }
)
public class FourWayFull extends PaneBlock {

    protected static final VoxelShape SHAPE = Block.makeCuboidShape(1.0D, 10.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    public FourWayFull(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.fullCube();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return VoxelShapes.empty();
    }

}
