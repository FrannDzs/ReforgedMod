package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FourWayBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

@Assets(
        state = @State(name = "%s_branch_vertical", template = "parent_branch_vertical"),
        item = @Model(name = "item/%s_branch_vertical", parent = "block/%s_branch_post", template = "item/parent_branch_vertical"),
        block = {
                @Model(name = "block/%s_branch_post", template = "block/parent_branch_post"),
                @Model(name = "block/%s_branch_diagonal", template = "block/parent_branch_diagonal"),
        }
)
public class BranchVertical extends FourWayBlock {

    private final VoxelShape[] renderShapes;

    public BranchVertical(Properties properties) {
        super(2.0F, 2.0F, 16.0F, 16.0F, 24.0F, properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(WATERLOGGED, false));
        this.renderShapes = this.makeShapes(2.0F, 1.0F, 16.0F, 6.0F, 15.0F);
    }

    private boolean attachesTo(BlockState blockState) {
        Block block = blockState.getBlock();
        boolean flag = block.isIn(BlockTags.LOGS);
        return block instanceof BranchSmallHorizontal || block instanceof BranchHorizontal || flag;
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return this.renderShapes[this.getIndex(state)];
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IWorldReader iworldreader = context.getWorld();
        BlockPos blockpos = context.getPos();
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        BlockPos north = blockpos.north();
        BlockPos east = blockpos.east();
        BlockPos south = blockpos.south();
        BlockPos west = blockpos.west();
        BlockState blockstateNorth = iworldreader.getBlockState(north);
        BlockState blockstateEast = iworldreader.getBlockState(east);
        BlockState blockstateSouth = iworldreader.getBlockState(south);
        BlockState blockstateWest = iworldreader.getBlockState(west);
        boolean boolNorth = this.attachesTo(blockstateNorth);
        boolean boolEast = this.attachesTo(blockstateEast);
        boolean boolSouth = this.attachesTo(blockstateSouth);
        boolean boolWest = this.attachesTo(blockstateWest);

        return this.getDefaultState()
                .with(NORTH, boolNorth)
                .with(EAST, boolEast)
                .with(SOUTH, boolSouth)
                .with(WEST, boolWest)
                .with(WATERLOGGED, Boolean.valueOf(ifluidstate.getFluid() == Fluids.WATER));
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        if (facing == Direction.DOWN) {
            return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        } else {
            boolean boolNorth = facing == Direction.NORTH ? this.attachesTo(facingState) || stateIn.get(NORTH) : stateIn.get(NORTH);
            boolean boolEast = facing == Direction.EAST ? this.attachesTo(facingState) || stateIn.get(EAST) : stateIn.get(EAST);
            boolean boolSouth = facing == Direction.SOUTH ? this.attachesTo(facingState) || stateIn.get(SOUTH) : stateIn.get(SOUTH);
            boolean boolWest = facing == Direction.WEST ? this.attachesTo(facingState) || stateIn.get(WEST) : stateIn.get(WEST);

            return stateIn
                    .with(NORTH, boolNorth)
                    .with(EAST, boolEast)
                    .with(SOUTH, boolSouth)
                    .with(WEST, boolWest);
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, WEST, SOUTH, WATERLOGGED);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
