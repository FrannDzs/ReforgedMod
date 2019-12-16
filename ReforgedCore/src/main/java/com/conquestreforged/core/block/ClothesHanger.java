package com.conquestreforged.core.block;

import com.conquestreforged.core.block.base.WaterloggedDirectionalShape;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.World;

public class ClothesHanger extends WaterloggedDirectionalShape {

    public static final IntegerProperty ACTIVATED = IntegerProperty.create("activated", 1, 4);
    private static final VoxelShape EAST_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
    private static final VoxelShape WEST_SHAPE = Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape SOUTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
    private static final VoxelShape NORTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 16.0D, 16.0D, 16.0D, 8.0D);

    public ClothesHanger(Block.Properties properties) {
        super(properties);
    }

    @Override
    protected void addProperties(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ACTIVATED);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return super.getStateForPlacement(context).with(ACTIVATED, 1);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!player.abilities.allowEdit) {
            return false;
        } else {
            worldIn.setBlockState(pos, state.cycle(ACTIVATED), 3);
            return true;
        }
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

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

}
