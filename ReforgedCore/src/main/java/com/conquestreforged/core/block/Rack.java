package com.conquestreforged.core.block;

import com.conquestreforged.core.block.base.WaterloggedDirectionalShape;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IWorld;

public class Rack extends WaterloggedDirectionalShape {

    private static final VoxelShape EAST_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
    private static final VoxelShape WEST_SHAPE = Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape SOUTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
    private static final VoxelShape NORTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 16.0D, 16.0D, 16.0D, 8.0D);

    public Rack(Properties properties) {
        super(properties);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    private boolean attachesTo(BlockState blockstate) {
        Block block = blockstate.getBlock();
        return !Block.cannotAttach(block) && (!(block != this && !(block instanceof Rack)));
    }

    private boolean canConnectTo(IWorld worldIn, BlockPos pos) {
        BlockState BlockState = worldIn.getBlockState(pos);
        Block block = BlockState.getBlock();
        return !Block.cannotAttach(block) && (!(block != this && !(block instanceof Rack)));
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
