package com.conquestreforged.core.block.standard;


import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.base.WaterloggedShape;
import com.conquestreforged.core.block.builder.Props;
import com.conquestreforged.core.capability.Caps;
import com.conquestreforged.core.capability.Capabilities;
import com.conquestreforged.core.capability.toggle.Toggle;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

@Assets(
        state = @State(name = "%s_layer", template = "parent_layer"),
        item = @Model(name = "item/%s_layer", parent = "block/%s_layer_height2", template = "item/snow"),
        block = {
                @Model(name = "block/%s_layer_height2", template = "block/snow_height2"),
                @Model(name = "block/%s_layer_height4", template = "block/snow_height4"),
                @Model(name = "block/%s_layer_height6", template = "block/snow_height6"),
                @Model(name = "block/%s_layer_height8", template = "block/snow_height8"),
                @Model(name = "block/%s_layer_height10", template = "block/snow_height10"),
                @Model(name = "block/%s_layer_height12", template = "block/snow_height12"),
                @Model(name = "block/%s_layer_height14", template = "block/snow_height14")
        }
)
public class Layer extends WaterloggedShape {

    public static final IntegerProperty LAYERS = IntegerProperty.create("layer", 1, 7);
    private static final VoxelShape[] BOTTOM_SHAPE = new VoxelShape[]{Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D)};

    private Block fullBlock;

    public Layer(Props props) {
        super(props.toProperties());
        this.fullBlock = props.getParent().getBlock();
        this.setDefaultState(this.stateContainer.getBaseState().with(LAYERS, Integer.valueOf(1)).with(WATERLOGGED, false));
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext context) {
        if (!context.replacingClickedOnBlock()) {
            return false;
        }

        ItemStack item = context.getItem();
        if (item.getItem() != this.asItem()) {
            return false;
        }

        int toggle = Caps.forPlayer(context, Capabilities.TOGGLE, Toggle::getIndex, -1);

        return toggle == 0 && context.getFace() == Direction.UP;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState p_200123_1_, IBlockReader p_200123_2_, BlockPos p_200123_3_) {
        return false;
    }


    @Override
    public VoxelShape getShape(BlockState state) {
        return BOTTOM_SHAPE[state.get(LAYERS) - 1];
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState state = context.getWorld().getBlockState(context.getPos());
        int toggle = Caps.forPlayer(context, Capabilities.TOGGLE, Toggle::getIndex, -1);
        if (state.getBlock() == this) {
            int i = state.get(LAYERS);
            if (i == 7) {
                return fullBlock.getDefaultState();
            }
            return state.with(LAYERS, Math.min(7, i + 1));
        } else {
            IFluidState fluid = context.getWorld().getFluidState(context.getPos());
            BlockState state2 = this.getDefaultState().with(WATERLOGGED, fluid.getFluid() == Fluids.WATER);
            if (toggle == 1) {
                state2 = state2.with(LAYERS, 2);
            } else if (toggle == 2) {
                state2 = state2.with(LAYERS, 3);
            } else if (toggle == 3) {
                state2 = state2.with(LAYERS, 4);
            } else if (toggle == 4) {
                state2 = state2.with(LAYERS, 5);
            } else if (toggle == 5) {
                state2 = state2.with(LAYERS, 6);
            } else if (toggle == 6) {
                state2 = state2.with(LAYERS, 7);
            }

            // TODO can't the above if's be replaced with something like this?
//            if (toggle > 0 && toggle < 6) {
//                state2 = state2.with(LAYERS, toggle + 1);
//            }

            return state2;
        }
    }

    @Override
    protected void addProperties(StateContainer.Builder<Block, BlockState> container) {
        container.add(LAYERS);
    }
}