package com.conquestreforged.core.block;

import com.conquestreforged.core.block.base.WaterloggedDirectionalShape;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class RackHalberds extends WaterloggedDirectionalShape {

    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;

    private static final VoxelShape EAST_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
    private static final VoxelShape WEST_SHAPE = Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape SOUTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
    private static final VoxelShape NORTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 16.0D, 16.0D, 16.0D, 8.0D);

    public RackHalberds(Block.Properties properties) {
        super(properties);
        this.setDefaultState((this.stateContainer.getBaseState())
                .with(UP, false)
                .with(DOWN, false)
                .with(DIRECTION, Direction.NORTH)
                .with(WATERLOGGED, false));
    }

    @Override
    protected void addProperties(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos pos = context.getPos();
        IBlockReader reader = context.getWorld();

        BlockState up = reader.getBlockState(pos.up());
        BlockState down = reader.getBlockState(pos.down());

        return super.getStateForPlacement(context)
                .with(UP, attachesTo(up))
                .with(DOWN, attachesTo(down));
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        boolean flag = this.canConnectTo(worldIn, currentPos.up());
        boolean flag1 = this.canConnectTo(worldIn, currentPos.down());
        return stateIn.with(UP, flag).with(DOWN, flag1);
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

    private boolean attachesTo(BlockState blockstate) {
        Block block = blockstate.getBlock();
        return !Block.cannotAttach(block) && (!(block != this && !(block instanceof RackHalberds)));
    }

    private boolean canConnectTo(IWorld worldIn, BlockPos pos) {
        BlockState BlockState = worldIn.getBlockState(pos);
        Block block = BlockState.getBlock();
        return !Block.cannotAttach(block) && (!(block != this && !(block instanceof RackHalberds)));
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
