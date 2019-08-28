package com.conquestreforged.core.block;

import com.conquestreforged.core.block.builder.Props;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

public class Crops extends CropsBlock {

    private final IItemProvider seeds;
    private final IItemProvider crop;

    public Crops(Props props) {
        super(props.toProperties());
        this.seeds = props.get("seeds", IItemProvider.class);
        this.crop = props.get("crop", IItemProvider.class);
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader reader, BlockPos pos) {
        return VoxelShapes.fullCube();
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        return true;
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        return true;
    }

    @Override
    protected IItemProvider getSeedsItem() {
        return seeds;
    }

    protected IItemProvider getCropsItem() {
        return crop;
    }
}
