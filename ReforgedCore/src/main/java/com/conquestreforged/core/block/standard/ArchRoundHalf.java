package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.properties.HalfArchShape;
import com.conquestreforged.core.block.properties.Waterloggable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.Half;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

@Assets(
        state = @State(name = "%s_round_arch_half", template = "parent_round_arch_half"),
        item = @Model(name = "item/%s_round_arch_half", parent = "block/%s_round_arch_one_half", template = "item/parent_round_arch_half"),
        block = {
                @Model(name = "block/%s_round_arch_one_half", template = "block/parent_round_arch_one_half"),
                @Model(name = "block/%s_round_arch_two_half_l", template = "block/parent_round_arch_two_half_l"),
                @Model(name = "block/%s_round_arch_two_half_r", template = "block/parent_round_arch_two_half_r"),
                @Model(name = "block/%s_round_arch_three_half_l", template = "block/parent_round_arch_three_half_l"),
                @Model(name = "block/%s_round_arch_three_half_r", template = "block/parent_round_arch_three_half_r"),
                @Model(name = "block/%s_round_arch_three_top_half", template = "block/parent_round_arch_three_top_half"),
                @Model(name = "block/%s_round_arch_one_half_bottom", template = "block/parent_round_arch_one_half_bottom"),
                @Model(name = "block/%s_round_arch_two_half_l_bottom", template = "block/parent_round_arch_two_half_l_bottom"),
                @Model(name = "block/%s_round_arch_two_half_r_bottom", template = "block/parent_round_arch_two_half_r_bottom"),
                @Model(name = "block/%s_round_arch_three_half_l_bottom", template = "block/parent_round_arch_three_half_l_bottom"),
                @Model(name = "block/%s_round_arch_three_half_r_bottom", template = "block/parent_round_arch_three_half_r_bottom"),
                @Model(name = "block/%s_round_arch_three_top_half_bottom", template = "block/parent_round_arch_three_top_half_bottom"),
        }
)
public class ArchRoundHalf extends HorizontalBlock implements Waterloggable {

    public static final EnumProperty FORM = EnumProperty.create("shape", HalfArchShape.class);
    public static final EnumProperty<Half> TYPE_UPDOWN = EnumProperty.create("type", Half.class);

    private static final VoxelShape EAST_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
    private static final VoxelShape WEST_SHAPE = Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape SOUTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
    private static final VoxelShape NORTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 16.0D, 16.0D, 16.0D, 8.0D);

    private static final VoxelShape ARCH_NORTH_R_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D));
    private static final VoxelShape ARCH_NORTH_L_SHAPE = VoxelShapes.or(Block.makeCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 8.0D, 8.0D, 8.0D, 16.0D, 16.0D));

    private static final VoxelShape ARCH_WEST_L_SHAPE = VoxelShapes.or(Block.makeCuboidShape(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D));
    private static final VoxelShape ARCH_WEST_R_SHAPE = VoxelShapes.or(Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D), Block.makeCuboidShape(8.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D));

    private static final VoxelShape ARCH_EAST_R_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D));
    private static final VoxelShape ARCH_EAST_L_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 8.0D));

    private static final VoxelShape ARCH_SOUTH_L_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D), Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D));
    private static final VoxelShape ARCH_SOUTH_R_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 8.0D), Block.makeCuboidShape(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D));

    private static final VoxelShape ARCH_MIDDLE_SOUTH_SHAPE = Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D);
    private static final VoxelShape ARCH_MIDDLE_NORTH_SHAPE = Block.makeCuboidShape(0.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape ARCH_MIDDLE_WEST_SHAPE = Block.makeCuboidShape(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape ARCH_MIDDLE_EAST_SHAPE = Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 16.0D);

    private static final VoxelShape ARCH_NORTH_R_BOTTOM_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D), Block.makeCuboidShape(0.0D, 8.0D, 8.0D, 8.0D, 16.0D, 16.0D));
    private static final VoxelShape ARCH_NORTH_L_BOTTOM_SHAPE = VoxelShapes.or(Block.makeCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D));

    private static final VoxelShape ARCH_WEST_L_BOTTOM_SHAPE = VoxelShapes.or(Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.makeCuboidShape(8.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D));
    private static final VoxelShape ARCH_WEST_R_BOTTOM_SHAPE = VoxelShapes.or(Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D), Block.makeCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D));

    private static final VoxelShape ARCH_EAST_R_BOTTOM_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 16.0D), Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 8.0D));
    private static final VoxelShape ARCH_EAST_L_BOTTOM_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D));

    private static final VoxelShape ARCH_SOUTH_L_BOTTOM_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D), Block.makeCuboidShape(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D));
    private static final VoxelShape ARCH_SOUTH_R_BOTTOM_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 8.0D), Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D));

    private static final VoxelShape ARCH_MIDDLE_SOUTH_BOTTOM_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
    private static final VoxelShape ARCH_MIDDLE_NORTH_BOTTOM_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);
    private static final VoxelShape ARCH_MIDDLE_WEST_BOTTOM_SHAPE = Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    private static final VoxelShape ARCH_MIDDLE_EAST_BOTTOM_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 16.0D);


    public ArchRoundHalf(Properties properties) {
        super(properties);
        this.setDefaultState((this.stateContainer.getBaseState()).with(TYPE_UPDOWN, Half.TOP).with(FORM, HalfArchShape.ONE).with(HORIZONTAL_FACING, Direction.NORTH).with(WATERLOGGED, false));
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        if (attachesTo(worldIn.getWorld().getBlockState(facingPos))) {
            IBlockReader iblockreader = worldIn.getWorld();
            BlockState north = iblockreader.getBlockState(currentPos.north());
            BlockState east = iblockreader.getBlockState(currentPos.east());
            BlockState south = iblockreader.getBlockState(currentPos.south());
            BlockState west = iblockreader.getBlockState(currentPos.west());

            int counter = 0;
            boolean isThirdShape = false;

            if (attachesTo(north)) {
                counter += 1;
                if (attachesTo(iblockreader.getBlockState(currentPos.north(2)))) {
                    isThirdShape = true;
                }
            }
            if (attachesTo(east)) {
                counter += 1;
                if (attachesTo(iblockreader.getBlockState(currentPos.east(2)))) {
                    isThirdShape = true;
                }
            }
            if (attachesTo(south)) {
                counter += 1;
                if (attachesTo(iblockreader.getBlockState(currentPos.south(2)))) {
                    isThirdShape = true;
                }
            }
            if (attachesTo(west)) {
                counter += 1;
                if (attachesTo(iblockreader.getBlockState(currentPos.west(2)))) {
                    isThirdShape = true;
                }
            }

            if (counter == 0) {
                return stateIn
                        .with(FORM, HalfArchShape.ONE);
            } else if (counter == 1 && isThirdShape) {
                return stateIn
                        .with(FORM, (attachesTo(north) || attachesTo(west)) ? HalfArchShape.THREE_L : HalfArchShape.THREE_R);
            } else if (counter == 1) {
                return stateIn
                        .with(FORM, (attachesTo(north) || attachesTo(west)) ? HalfArchShape.TWO_L : HalfArchShape.TWO_R);
            } else if (counter >= 2) {
                return stateIn
                        .with(FORM, HalfArchShape.THREE_MIDDLE);
            } else {
                return stateIn;
            }
        }
        return stateIn;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }


    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.get(TYPE_UPDOWN) == Half.TOP) {
            if (state.get(FORM) == HalfArchShape.ONE) {
                switch (state.get(HORIZONTAL_FACING)) {
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
            } else if (state.get(FORM) == HalfArchShape.TWO_L || state.get(FORM) == HalfArchShape.THREE_L) {
                switch (state.get(HORIZONTAL_FACING)) {
                    case NORTH:
                    default:
                        return ARCH_NORTH_L_SHAPE;
                    case SOUTH:
                        return ARCH_SOUTH_L_SHAPE;
                    case WEST:
                        return ARCH_WEST_L_SHAPE;
                    case EAST:
                        return ARCH_EAST_L_SHAPE;
                }
            } else if (state.get(FORM) == HalfArchShape.TWO_R || state.get(FORM) == HalfArchShape.THREE_R) {
                switch (state.get(HORIZONTAL_FACING)) {
                    case NORTH:
                    default:
                        return ARCH_NORTH_R_SHAPE;
                    case SOUTH:
                        return ARCH_SOUTH_R_SHAPE;
                    case WEST:
                        return ARCH_WEST_R_SHAPE;
                    case EAST:
                        return ARCH_EAST_R_SHAPE;
                }
            } else {
                switch (state.get(HORIZONTAL_FACING)) {
                    case NORTH:
                    default:
                        return ARCH_MIDDLE_NORTH_SHAPE;
                    case SOUTH:
                        return ARCH_MIDDLE_SOUTH_SHAPE;
                    case WEST:
                        return ARCH_MIDDLE_WEST_SHAPE;
                    case EAST:
                        return ARCH_MIDDLE_EAST_SHAPE;
                }
            }
        } else {
            if (state.get(FORM) == HalfArchShape.ONE) {
                switch (state.get(HORIZONTAL_FACING)) {
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
            } else if (state.get(FORM) == HalfArchShape.TWO_L || state.get(FORM) == HalfArchShape.THREE_L) {
                switch (state.get(HORIZONTAL_FACING)) {
                    case NORTH:
                    default:
                        return ARCH_NORTH_L_BOTTOM_SHAPE;
                    case SOUTH:
                        return ARCH_SOUTH_L_BOTTOM_SHAPE;
                    case WEST:
                        return ARCH_WEST_L_BOTTOM_SHAPE;
                    case EAST:
                        return ARCH_EAST_L_BOTTOM_SHAPE;
                }
            } else if (state.get(FORM) == HalfArchShape.TWO_R || state.get(FORM) == HalfArchShape.THREE_R) {
                switch (state.get(HORIZONTAL_FACING)) {
                    case NORTH:
                    default:
                        return ARCH_NORTH_R_BOTTOM_SHAPE;
                    case SOUTH:
                        return ARCH_SOUTH_R_BOTTOM_SHAPE;
                    case WEST:
                        return ARCH_WEST_R_BOTTOM_SHAPE;
                    case EAST:
                        return ARCH_EAST_R_BOTTOM_SHAPE;
                }
            } else {
                switch (state.get(HORIZONTAL_FACING)) {
                    case NORTH:
                    default:
                        return ARCH_MIDDLE_NORTH_BOTTOM_SHAPE;
                    case SOUTH:
                        return ARCH_MIDDLE_SOUTH_BOTTOM_SHAPE;
                    case WEST:
                        return ARCH_MIDDLE_WEST_BOTTOM_SHAPE;
                    case EAST:
                        return ARCH_MIDDLE_EAST_BOTTOM_SHAPE;
                }
            }
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());

        BlockPos pos = context.getPos();
        IBlockReader iblockreader = context.getWorld();
        BlockState north = iblockreader.getBlockState(pos.north());
        BlockState east = iblockreader.getBlockState(pos.east());
        BlockState south = iblockreader.getBlockState(pos.south());
        BlockState west = iblockreader.getBlockState(pos.west());

        int counter = 0;
        boolean isThirdShape = false;
        Direction facing = context.getPlacementHorizontalFacing().getOpposite();
        HalfArchShape shape = HalfArchShape.ONE;

        Direction facingVertical = context.getFace();
        BlockState state2;
        if (facingVertical != Direction.DOWN && (facingVertical == Direction.UP || context.getHitVec().y <= 0.5D)) {
            state2 = this.getDefaultState().with(TYPE_UPDOWN, Half.BOTTOM);
        } else {
            state2 = this.getDefaultState().with(TYPE_UPDOWN, Half.TOP);
        }

        if (attachesTo(north)) {
            counter += 1;
            shape = HalfArchShape.TWO_L;
            if (attachesTo(iblockreader.getBlockState(pos.north(2)))) {
                isThirdShape = true;
                shape = HalfArchShape.THREE_L;
            }
        }
        if (attachesTo(east)) {
            counter += 1;
            shape = HalfArchShape.TWO_L;
            if (attachesTo(iblockreader.getBlockState(pos.east(2)))) {
                isThirdShape = true;
                shape = HalfArchShape.THREE_L;
            }
        }
        if (attachesTo(south)) {
            counter += 1;
            shape = HalfArchShape.TWO_R;
            if (attachesTo(iblockreader.getBlockState(pos.south(2)))) {
                isThirdShape = true;
                shape = HalfArchShape.THREE_R;
            }
        }
        if (attachesTo(west)) {
            counter += 1;
            shape = HalfArchShape.TWO_R;
            if (attachesTo(iblockreader.getBlockState(pos.west(2)))) {
                isThirdShape = true;
                shape = HalfArchShape.THREE_R;
            }
        }

        if (counter == 0) {
            return state2
                    .with(HORIZONTAL_FACING, facing)
                    .with(FORM, shape)
                    .with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
        } else if (counter == 1 && isThirdShape) {
            return state2
                    .with(HORIZONTAL_FACING, facing)
                    .with(FORM, shape)
                    .with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
        } else if (counter == 1) {
            return state2
                    .with(HORIZONTAL_FACING, facing)
                    .with(FORM, shape)
                    .with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
        } else {
            return state2
                    .with(HORIZONTAL_FACING, facing)
                    .with(FORM, HalfArchShape.THREE_MIDDLE)
                    .with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
        }

    }

    @Override
    public IFluidState getFluidState(BlockState state) {
        return Waterloggable.getFluidState(state);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, TYPE_UPDOWN, FORM, WATERLOGGED);
    }

    private boolean attachesTo(BlockState state) {
        Block block = state.getBlock();
        return block instanceof ArchRoundHalf;
    }
}