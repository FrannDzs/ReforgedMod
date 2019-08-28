package com.conquestreforged.core.block;

import com.conquestreforged.core.block.properties.Waterloggable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.SnowBlock;
import net.minecraft.fluid.IFluidState;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockReader;

public class Bush extends BushBlock implements Waterloggable {
    public Bush(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(WATERLOGGED, false));

    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return true;
    }

    @Override
    public Vec3d getOffset(BlockState state, IBlockReader worldIn, BlockPos pos) {
        long i = MathHelper.getCoordinateRandom(pos.getX(), 0, pos.getZ());
        double x = ((double)((float)(i & 15L) / 15.0F) - 0.5D) * 0.5D;
        double z = ((double)((float)(i >> 8 & 15L) / 15.0F) - 0.5D) * 0.5D;

        if (worldIn.getBlockState(pos.down()).getBlock() instanceof SnowBlock) {
            if (worldIn.getBlockState(pos.down()).get(SnowBlock.LAYERS) == 7) {
                return new Vec3d(x, -0.1D, z);
            } else if (worldIn.getBlockState(pos.down()).get(SnowBlock.LAYERS) == 6) {
                return new Vec3d(x, -0.25D, z);
            } else if (worldIn.getBlockState(pos.down()).get(SnowBlock.LAYERS) == 5) {
                return new Vec3d(x, -0.4D, z);
            } else if (worldIn.getBlockState(pos.down()).get(SnowBlock.LAYERS) == 4) {
                return new Vec3d(x, -0.52D, z);
            } else if (worldIn.getBlockState(pos.down()).get(SnowBlock.LAYERS) == 3) {
                return new Vec3d(x, -0.65D, z);
            } else if (worldIn.getBlockState(pos.down()).get(SnowBlock.LAYERS) == 2) {
                return new Vec3d(x, -0.75D, z);
            } else if (worldIn.getBlockState(pos.down()).get(SnowBlock.LAYERS) == 1) {
                return new Vec3d(x, -0.9D, z);
            }
        }
        return new Vec3d(x, ((double)((float)(i >> 4 & 15L) / 15.0F) - 1.0D) * 0.2D, z);
    }

    @Override
    public IFluidState getFluidState(BlockState state) {
        return Waterloggable.getFluidState(state);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }
}
