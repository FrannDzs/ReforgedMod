package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
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
        state = @State(name = "%s_branch_small_vertical", template = "parent_branch_small_vertical"),
        item = @Model(name = "item/%s_branch_small_vertical", parent = "block/%s_branch_small_post", template = "item/parent_branch_small_vertical"),
        block = {
                @Model(name = "block/%s_branch_small_post", template = "block/parent_branch_small_post"),
                @Model(name = "block/%s_branch_small_diagonal", template = "block/parent_branch_small_diagonal"),
                @Model(name = "block/%s_branch_small_twigs", template = "block/parent_branch_small_twigs"),
                @Model(name = "block/%s_branch_small_twigs_1", template = "block/parent_branch_small_twigs_1"),
                @Model(name = "block/%s_branch_small_twigs_2", template = "block/parent_branch_small_twigs_2"),
                @Model(name = "block/%s_branch_small_twigs_3", template = "block/parent_branch_small_twigs_3"),
        }
)
public class BranchSmallVertical extends BranchVertical {

    public static final BooleanProperty LEAVES = BooleanProperty.create("leaves");
    private final VoxelShape[] renderShapes;

    public BranchSmallVertical(Block.Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(NORTH, Boolean.valueOf(false)).with(EAST, Boolean.valueOf(false)).with(SOUTH, Boolean.valueOf(false)).with(WEST, Boolean.valueOf(false)).with(LEAVES, Boolean.valueOf(false)).with(WATERLOGGED, Boolean.valueOf(false)));
        this.renderShapes = this.makeShapes(2.0F, 1.0F, 16.0F, 6.0F, 15.0F);
    }

    private boolean attachesToLeaves(BlockState blockState) {
        Block block = blockState.getBlock();
        boolean flag = block.isIn(BlockTags.LEAVES);
        return flag;
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
        boolean boolLeavesNorth = this.attachesToLeaves(blockstateNorth);
        boolean boolLeavesEast = this.attachesToLeaves(blockstateEast);
        boolean boolLeavesSouth = this.attachesToLeaves(blockstateSouth);
        boolean boolLeavesWest = this.attachesToLeaves(blockstateWest);
        boolean boolLeaves = (boolLeavesNorth || boolLeavesEast || boolLeavesSouth || boolLeavesWest);

        return super.getStateForPlacement(context)
                .with(LEAVES, boolLeaves)
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
            boolean boolLeaves = stateIn.get(LEAVES);
            if (!boolLeaves) {
                boolLeaves = this.attachesToLeaves(facingState);
            }

            return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos)
                    .with(LEAVES, boolLeaves);
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, WEST, SOUTH, LEAVES, WATERLOGGED);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
