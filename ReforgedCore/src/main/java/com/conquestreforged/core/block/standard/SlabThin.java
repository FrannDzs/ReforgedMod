package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.properties.Waterloggable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.Half;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

@Assets(
        state = @State(name = "%s_thin_slab", template = "parent_slab_thin"),
        item = @Model(name = "item/%s_thin_slab", parent = "block/%s_slab_thin", template = "item/parent_slab_thin"),
        block = {
                @Model(name = "block/%s_slab_thin", template = "block/parent_slab_thin"),
        }
)
public class SlabThin extends Block implements Waterloggable {

    private static final VoxelShape BOTTOM_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);
    private static final VoxelShape TOP_SHAPE = Block.makeCuboidShape(0.0D, 13.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public static final EnumProperty<Half> TYPE_UPDOWN = EnumProperty.create("type", Half.class);

    public SlabThin(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(TYPE_UPDOWN, Half.BOTTOM).with(WATERLOGGED, false));
    }

    @Override
    public int getOpacity(BlockState state, IBlockReader reader, BlockPos pos) {
        return reader.getMaxLightLevel();
    }

    @Override
    public boolean propagatesSkylightDown(BlockState p_200123_1_, IBlockReader p_200123_2_, BlockPos p_200123_3_) {
        return false;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> container) {
        container.add(TYPE_UPDOWN, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        if (state.get(TYPE_UPDOWN) == Half.TOP) {
            return TOP_SHAPE;
        } else {
            return BOTTOM_SHAPE;
        }
    }

    @Override
    public boolean isSolid(BlockState state) {
        return state.get(TYPE_UPDOWN) == Half.TOP;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IFluidState fluid = context.getWorld().getFluidState(context.getPos());
        BlockState state2 = this.getDefaultState().with(TYPE_UPDOWN, Half.BOTTOM).with(WATERLOGGED, fluid.getFluid() == Fluids.WATER);
        Direction facing = context.getFace();
        return facing != Direction.DOWN && (facing == Direction.UP || context.getHitVec().y <= 0.5D) ? state2 : state2.with(TYPE_UPDOWN, Half.TOP);
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext context) {
        ItemStack item = context.getItem();
        if (item.getItem() == this.asItem()) {
            if (context.replacingClickedOnBlock()) {
                boolean posBool = context.getHitVec().y > 0.5D;
                Direction facing = context.getFace();
                if (state.get(TYPE_UPDOWN) == Half.BOTTOM) {
                    return facing == Direction.UP || posBool && facing.getAxis().isHorizontal();
                } else {
                    return facing == Direction.DOWN || !posBool && facing.getAxis().isHorizontal();
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader reader, BlockPos pos, PathType pathType) {
        switch(pathType.ordinal()) {
            case 1:
                return state.get(TYPE_UPDOWN) == Half.BOTTOM;
            case 2:
                return reader.getFluidState(pos).isTagged(FluidTags.WATER);
            case 3:
                return false;
            default:
                return false;
        }
    }

    @Override
    public IFluidState getFluidState(BlockState state) {
        return Waterloggable.getFluidState(state);
    }
}