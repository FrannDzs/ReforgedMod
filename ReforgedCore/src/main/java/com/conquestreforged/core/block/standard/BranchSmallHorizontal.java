package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
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
        state = @State(name = "%s_branch_small_horizontal", template = "parent_branch_small_horizontal"),
        item = @Model(name = "item/%s_branch_small_horizontal", parent = "block/%s_branch_small_horizontal_post", template = "item/parent_branch_small_horizontal"),
        block = {
                @Model(name = "block/%s_branch_small_horizontal_post", template = "block/parent_branch_small_horizontal_post"),
                @Model(name = "block/%s_branch_small_horizontal_diagonal", template = "block/parent_branch_small_horizontal_diagonal"),
                @Model(name = "block/%s_branch_small_horizontal_twigs", template = "block/parent_branch_small_horizontal_twigs"),
                @Model(name = "block/%s_branch_small_horizontal_twigs1", template = "block/parent_branch_small_horizontal_twigs1"),
                @Model(name = "block/%s_branch_small_horizontal_twigs2", template = "block/parent_branch_small_horizontal_twigs2"),
                @Model(name = "block/%s_branch_small_horizontal_up", template = "block/parent_branch_small_horizontal_diagonal_up")
        }
)
public class BranchSmallHorizontal extends BranchHorizontal {

    public static final BooleanProperty LEAVES = BooleanProperty.create("leaves");

    private static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 4.0D, 0.0D, 16.0D, 12.0D, 16.0D);

    public BranchSmallHorizontal(Block.Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(SIDE1,false).with(SIDE2,false).with(UP,false).with(LEAVES, false).with(WATERLOGGED, Boolean.valueOf(false)));
    }

    @Override
    public VoxelShape getShape(BlockState state) {
        return SHAPE;
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
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IWorldReader iworldreader = context.getWorld();
        BlockPos blockpos = context.getPos();
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
                .with(LEAVES, boolLeaves);
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
    protected void addProperties(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(SIDE1, SIDE2, UP, LEAVES);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
