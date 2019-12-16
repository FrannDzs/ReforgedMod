package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.base.WaterloggedDirectionalShape;
import com.conquestreforged.core.block.properties.Waterloggable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.state.properties.Half;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

@Assets(
        state = @State(name = "%s_vertical_corner_slab", template = "parent_vertical_corner_slab"),
        item = @Model(name = "item/%s_vertical_corner_slab", parent = "block/%s_vertical_corner_slab_left", template = "item/parent_slab_corner"),
        block = {
                @Model(name = "block/%s_vertical_corner_slab_left", template = "block/parent_vertical_corner_slab_left"),
                @Model(name = "block/%s_vertical_corner_slab_bottom_left", template = "block/parent_vertical_corner_slab_bottom_left"),
                @Model(name = "block/%s_vertical_corner_slab_right", template = "block/parent_vertical_corner_slab_right"),
                @Model(name = "block/%s_vertical_corner_slab_bottom_right", template = "block/parent_vertical_corner_slab_bottom_right"),
        }
)
public class VerticalSlabCorner extends WaterloggedDirectionalShape implements Waterloggable {

    public static final EnumProperty<Half> TYPE_UPDOWN = EnumProperty.create("type", Half.class);
    public static final EnumProperty<DoorHingeSide> HINGE = BlockStateProperties.DOOR_HINGE;

    private static final VoxelShape ARCH_NORTH_R_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D));
    private static final VoxelShape ARCH_NORTH_L_SHAPE = VoxelShapes.or(Block.makeCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 8.0D, 8.0D, 8.0D, 16.0D, 16.0D));

    private static final VoxelShape ARCH_WEST_L_SHAPE = VoxelShapes.or(Block.makeCuboidShape(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D));
    private static final VoxelShape ARCH_WEST_R_SHAPE = VoxelShapes.or(Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D), Block.makeCuboidShape(8.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D));

    private static final VoxelShape ARCH_EAST_R_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D));
    private static final VoxelShape ARCH_EAST_L_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 8.0D));

    private static final VoxelShape ARCH_SOUTH_L_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D), Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D));
    private static final VoxelShape ARCH_SOUTH_R_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 8.0D), Block.makeCuboidShape(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D));

    private static final VoxelShape ARCH_NORTH_R_BOTTOM_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D), Block.makeCuboidShape(0.0D, 8.0D, 8.0D, 8.0D, 16.0D, 16.0D));
    private static final VoxelShape ARCH_NORTH_L_BOTTOM_SHAPE = VoxelShapes.or(Block.makeCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D));

    private static final VoxelShape ARCH_WEST_L_BOTTOM_SHAPE = VoxelShapes.or(Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.makeCuboidShape(8.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D));
    private static final VoxelShape ARCH_WEST_R_BOTTOM_SHAPE = VoxelShapes.or(Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D), Block.makeCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D));

    private static final VoxelShape ARCH_EAST_R_BOTTOM_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 16.0D), Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 8.0D));
    private static final VoxelShape ARCH_EAST_L_BOTTOM_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D));

    private static final VoxelShape ARCH_SOUTH_L_BOTTOM_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D), Block.makeCuboidShape(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D));
    private static final VoxelShape ARCH_SOUTH_R_BOTTOM_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 8.0D), Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D));

    public VerticalSlabCorner(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(DIRECTION, Direction.NORTH).with(TYPE_UPDOWN, Half.BOTTOM).with(WATERLOGGED, false));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IFluidState fluid = context.getWorld().getFluidState(context.getPos());
        Direction facingHorizontal = context.getPlacementHorizontalFacing().getOpposite();
        BlockState state2 = getDefaultState().with(DIRECTION, facingHorizontal).with(TYPE_UPDOWN, Half.BOTTOM).with(WATERLOGGED, fluid.getFluid() == Fluids.WATER);
        Direction facing = context.getFace();
        return facing != Direction.DOWN && (facing == Direction.UP || context.getHitVec().y <= 0.5D) ? state2.with(HINGE, this.getHingeSide(context)) : state2.with(TYPE_UPDOWN, Half.TOP).with(HINGE, this.getHingeSide(context));
    }

    private DoorHingeSide getHingeSide(BlockItemUseContext p_208073_1_) {
        IBlockReader iblockreader = p_208073_1_.getWorld();
        BlockPos blockpos = p_208073_1_.getPos();
        Direction direction = p_208073_1_.getPlacementHorizontalFacing();
        BlockPos blockpos1 = blockpos.up();
        Direction direction1 = direction.rotateYCCW();
        BlockPos blockpos2 = blockpos.offset(direction1);
        BlockState blockstate = iblockreader.getBlockState(blockpos2);
        BlockPos blockpos3 = blockpos1.offset(direction1);
        BlockState blockstate1 = iblockreader.getBlockState(blockpos3);
        Direction direction2 = direction.rotateY();
        BlockPos blockpos4 = blockpos.offset(direction2);
        BlockState blockstate2 = iblockreader.getBlockState(blockpos4);
        BlockPos blockpos5 = blockpos1.offset(direction2);
        BlockState blockstate3 = iblockreader.getBlockState(blockpos5);
        int i = (blockstate.func_224756_o(iblockreader, blockpos2) ? -1 : 0) + (blockstate1.func_224756_o(iblockreader, blockpos3) ? -1 : 0) + (blockstate2.func_224756_o(iblockreader, blockpos4) ? 1 : 0) + (blockstate3.func_224756_o(iblockreader, blockpos5) ? 1 : 0);
        if ( i <= 0) {
            if ( i >= 0) {
                int j = direction.getXOffset();
                int k = direction.getZOffset();
                Vec3d vec3d = p_208073_1_.getHitVec();
                double d0 = vec3d.x - (double)blockpos.getX();
                double d1 = vec3d.z - (double)blockpos.getZ();
                return (j >= 0 || !(d1 < 0.5D)) && (j <= 0 || !(d1 > 0.5D)) && (k >= 0 || !(d0 > 0.5D)) && (k <= 0 || !(d0 < 0.5D)) ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT;
            } else {
                return DoorHingeSide.LEFT;
            }
        } else {
            return DoorHingeSide.RIGHT;
        }
    }

    @Override
    public IFluidState getFluidState(BlockState state) {
        return Waterloggable.getFluidState(state);
    }

    @Override
    protected void addProperties(StateContainer.Builder<Block, BlockState> container) {
        container.add(TYPE_UPDOWN).add(HINGE);
    }

    @Override
    public VoxelShape getShape(BlockState state) {
        if (state.get(TYPE_UPDOWN) == Half.TOP) {
            if (state.get(HINGE) == DoorHingeSide.LEFT) {
                switch (state.get(DIRECTION)) {
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
            } else {
                switch (state.get(DIRECTION)) {
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
            }
        } else {
            if (state.get(HINGE) == DoorHingeSide.LEFT) {
                switch (state.get(DIRECTION)) {
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
            } else {
                switch (state.get(DIRECTION)) {
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
            }
        }
    }
}