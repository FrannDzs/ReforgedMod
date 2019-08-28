package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.base.WaterloggedDirectionalShape;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.Half;
import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShape;

@Assets(
        state = @State(name = "%s_eighth_slab", template = "parent_slab_eighth"),
        item = @Model(name = "item/%s_eighth_slab", parent = "block/%s_slab_eighth", template = "item/parent_slab_eighth"),
        block = {
                @Model(name = "block/%s_slab_eighth", template = "block/parent_slab_eighth"),
        }
)
public class SlabEighth extends WaterloggedDirectionalShape {

    private static final VoxelShape BOTTOM_QTR_EAST_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D);
    private static final VoxelShape BOTTOM_QTR_WEST_SHAPE = Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
    private static final VoxelShape BOTTOM_QTR_SOUTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D);
    private static final VoxelShape BOTTOM_QTR_NORTH_SHAPE = Block.makeCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);

    private static final VoxelShape TOP_QTR_EAST_SHAPE = Block.makeCuboidShape(0.0D, 8.0D, 8.0D, 8.0D, 16.0D, 16.0D);
    private static final VoxelShape TOP_QTR_WEST_SHAPE = Block.makeCuboidShape(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D);
    private static final VoxelShape TOP_QTR_SOUTH_SHAPE = Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 8.0D);
    private static final VoxelShape TOP_QTR_NORTH_SHAPE = Block.makeCuboidShape(8.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D);


    public static final EnumProperty<Half> TYPE_UPDOWN = EnumProperty.create("type", Half.class);

    public SlabEighth(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(DIRECTION, Direction.NORTH).with(TYPE_UPDOWN, Half.BOTTOM).with(WATERLOGGED, false));
    }

    @Override
    protected void addProperties(StateContainer.Builder<Block, BlockState> container) {
        container.add(TYPE_UPDOWN);
    }

    public VoxelShape getShape(BlockState state) {
        if (state.get(TYPE_UPDOWN) == Half.BOTTOM) {
            switch (state.get(DIRECTION)) {
                case NORTH:
                default:
                    return BOTTOM_QTR_NORTH_SHAPE;
                case SOUTH:
                    return BOTTOM_QTR_SOUTH_SHAPE;
                case WEST:
                    return BOTTOM_QTR_WEST_SHAPE;
                case EAST:
                    return BOTTOM_QTR_EAST_SHAPE;
            }
        } else {
            switch (state.get(DIRECTION)) {
                case NORTH:
                default:
                    return TOP_QTR_NORTH_SHAPE;
                case SOUTH:
                    return TOP_QTR_SOUTH_SHAPE;
                case WEST:
                    return TOP_QTR_WEST_SHAPE;
                case EAST:
                    return TOP_QTR_EAST_SHAPE;
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IFluidState fluid = context.getWorld().getFluidState(context.getPos());
        Direction facingHorizontal = context.getPlacementHorizontalFacing().getOpposite();
        BlockState state2 = getDefaultState().with(DIRECTION, facingHorizontal).with(TYPE_UPDOWN, Half.BOTTOM).with(WATERLOGGED, fluid.getFluid() == Fluids.WATER);
        Direction facing = context.getFace();
        return facing != Direction.DOWN && (facing == Direction.UP || context.getHitVec().y <= 0.5D) ? state2 : state2.with(TYPE_UPDOWN, Half.TOP);
    }
}