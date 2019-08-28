package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.base.WaterloggedDirectionalShape;
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
        state = @State(name = "%s_branch_horizontal", template = "parent_branch_horizontal"),
        item = @Model(name = "item/%s_branch_horizontal", parent = "block/%s_branch_horizontal_post", template = "item/parent_branch_horizontal"),
        block = {
                @Model(name = "block/%s_branch_horizontal_post", template = "block/parent_branch_horizontal_post"),
                @Model(name = "block/%s_branch_horizontal_diagonal", template = "block/parent_branch_horizontal_diagonal"),
                @Model(name = "block/%s_branch_horizontal_diagonal_up", template = "block/parent_branch_horizontal_diagonal_up")
        }
)
public class BranchHorizontal extends WaterloggedDirectionalShape {

    public static final BooleanProperty SIDE1 = BooleanProperty.create("side1");
    public static final BooleanProperty SIDE2 = BooleanProperty.create("side2");
    public static final BooleanProperty UP = BooleanProperty.create("up");

    private static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 4.0D, 0.0D, 16.0D, 12.0D, 16.0D);

    public BranchHorizontal(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(SIDE1,false).with(SIDE2,false).with(UP,false).with(WATERLOGGED, Boolean.valueOf(false)));
    }

    @Override
    public VoxelShape getShape(BlockState state) {
        return SHAPE;
    }

    private boolean attachesTo(BlockState blockState) {
        Block block = blockState.getBlock();
        boolean flag = block.isIn(BlockTags.LOGS);
        return block instanceof BranchHorizontal || block instanceof BranchSmallHorizontal || flag;
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction facing = context.getPlacementHorizontalFacing().getOpposite();
        IWorldReader iworldreader = context.getWorld();
        BlockPos blockpos = context.getPos();
        BlockPos north = blockpos.north();
        BlockPos east = blockpos.east();
        BlockPos south = blockpos.south();
        BlockPos west = blockpos.west();
        BlockPos up = blockpos.up();
        BlockState blockstateNorth = iworldreader.getBlockState(north);
        BlockState blockstateEast = iworldreader.getBlockState(east);
        BlockState blockstateSouth = iworldreader.getBlockState(south);
        BlockState blockstateWest = iworldreader.getBlockState(west);
        BlockState blockstateUp = iworldreader.getBlockState(up);
        boolean boolNorth = this.attachesTo(blockstateNorth);
        boolean boolEast = this.attachesTo(blockstateEast);
        boolean boolSouth = this.attachesTo(blockstateSouth);
        boolean boolWest = this.attachesTo(blockstateWest);
        boolean boolSide1 = false;
        boolean boolSide2 = false;
        switch (facing) {
            case NORTH:
                boolSide1 = boolEast;
                boolSide2 = boolWest;
                break;
            case EAST:
                boolSide1 = boolNorth;
                boolSide2 = boolSouth;
                break;
            case SOUTH:
                boolSide1 = boolWest;
                boolSide2 = boolEast;
                break;
            case WEST:
                boolSide1 = boolSouth;
                boolSide2 = boolNorth;
            default:
                break;
        }
        Block blockUp = blockstateUp.getBlock();
        boolean boolUp = blockUp instanceof BranchSmallVertical || blockUp instanceof BranchVertical;

        return super.getStateForPlacement(context)
                .with(SIDE1, boolSide1)
                .with(SIDE2, boolSide2)
                .with(SIDE2, boolUp);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        if (facing == Direction.DOWN) {
            return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        } else {
            Direction blockFacing = stateIn.get(DIRECTION);
            boolean boolNorth = facing == Direction.NORTH && this.attachesTo(facingState);
            boolean boolEast = facing == Direction.EAST && this.attachesTo(facingState);
            boolean boolSouth = facing == Direction.SOUTH && this.attachesTo(facingState);
            boolean boolWest = facing == Direction.WEST && this.attachesTo(facingState);
            boolean boolUp = facing == Direction.UP ? (facingState.getBlock() instanceof BranchSmallVertical || facingState.getBlock() instanceof BranchVertical) || stateIn.get(UP) : stateIn.get(UP);
            boolean boolSide1 = stateIn.get(SIDE1);
            boolean boolSide2 = stateIn.get(SIDE2);
            switch (blockFacing) {
                case NORTH:
                    boolSide1 = stateIn.get(SIDE1) || boolEast;
                    boolSide2 = stateIn.get(SIDE2) || boolWest;
                    break;
                case EAST:
                    boolSide1 = stateIn.get(SIDE1) || boolNorth;
                    boolSide2 = stateIn.get(SIDE2) || boolSouth;
                    break;
                case SOUTH:
                    boolSide1 = stateIn.get(SIDE1) || boolWest;
                    boolSide2 = stateIn.get(SIDE2) || boolEast;
                    break;
                case WEST:
                    boolSide1 = stateIn.get(SIDE1) || boolSouth;
                    boolSide2 = stateIn.get(SIDE2) || boolNorth;
                default:
                    break;
            }

            return stateIn
                    .with(SIDE1, boolSide1)
                    .with(SIDE2, boolSide2)
                    .with(UP, boolUp);
        }
    }

    @Override
    protected void addProperties(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(SIDE1, SIDE2, UP);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
