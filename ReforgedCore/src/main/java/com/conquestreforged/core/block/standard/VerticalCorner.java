package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.base.WaterloggedDirectionalShape;
import com.conquestreforged.core.block.builder.Props;
import com.conquestreforged.core.capability.utils.Caps;
import com.conquestreforged.core.capability.Capabilities;
import com.conquestreforged.core.capability.toggle.Toggle;
import com.conquestreforged.core.block.properties.Waterloggable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

@Assets(
        state = @State(name = "%s_vertical_corner", template = "parent_vertical_corner"),
        item = @Model(name = "item/%s_vertical_corner", parent = "block/%s_vertical_corner_4", template = "item/parent_vertical_corner"),
        block = {
                @Model(name = "block/%s_vertical_corner_2", template = "block/parent_vertical_corner_2"),
                @Model(name = "block/%s_vertical_corner_4", template = "block/parent_vertical_corner_4"),
                @Model(name = "block/%s_vertical_corner_6", template = "block/parent_vertical_corner_6"),
        }
)
public class VerticalCorner extends WaterloggedDirectionalShape implements Waterloggable {

    public static final IntegerProperty LAYERS = IntegerProperty.create("layer", 1, 3);

    private static final VoxelShape EAST[] = new VoxelShape[]{Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 4.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 12.0D, 16.0D, 16.0D)};
    private static final VoxelShape QTR_EAST[] = new VoxelShape[]{Block.makeCuboidShape(4.0D, 0.0D, 12.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(12.0D, 0.0D, 4.0D, 16.0D, 16.0D, 16.0D)};
    private static final VoxelShape EAST_SHAPE[] = new VoxelShape[]{VoxelShapes.or(EAST[0], QTR_EAST[0]), VoxelShapes.or(EAST[1], QTR_EAST[1]), VoxelShapes.or(EAST[2], QTR_EAST[2])};

    private static final VoxelShape WEST[] = new VoxelShape[]{Block.makeCuboidShape(12.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(4.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};
    private static final VoxelShape QTR_WEST[] = new VoxelShape[]{Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 12.0D, 16.0D, 4.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 8.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 4.0D, 16.0D, 12.0D)};
    private static final VoxelShape WEST_SHAPE[] = new VoxelShape[]{VoxelShapes.or(WEST[0], QTR_WEST[0]), VoxelShapes.or(WEST[1], QTR_WEST[1]), VoxelShapes.or(WEST[2], QTR_WEST[2])};

    private static final VoxelShape NORTH[] = new VoxelShape[]{Block.makeCuboidShape(0.0D, 0.0D, 12.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 4.0D, 16.0D, 16.0D, 16.0D)};
    private static final VoxelShape QTR_NORTH[] = new VoxelShape[]{Block.makeCuboidShape(12.0D, 0.0D, 0.0D, 16.0D, 16.0D, 12.0D), Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D), Block.makeCuboidShape(4.0D, 0.0D, 0.0D, 16.0D, 16.0D, 4.0D)};
    private static final VoxelShape NORTH_SHAPE[] = new VoxelShape[]{VoxelShapes.or(NORTH[0], QTR_NORTH[0]), VoxelShapes.or(NORTH[1], QTR_NORTH[1]), VoxelShapes.or(NORTH[2], QTR_NORTH[2])};

    private static final VoxelShape SOUTH[] = new VoxelShape[]{Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 4.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 12.0D)};
    private static final VoxelShape QTR_SOUTH[] = new VoxelShape[]{Block.makeCuboidShape(0.0D, 0.0D, 4.0D, 4.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 12.0D, 12.0D, 16.0D, 16.0D)};
    private static final VoxelShape SOUTH_SHAPE[] = new VoxelShape[]{VoxelShapes.or(SOUTH[0], QTR_SOUTH[0]), VoxelShapes.or(SOUTH[1], QTR_SOUTH[1]), VoxelShapes.or(SOUTH[2], QTR_SOUTH[2])};

    private Block fullBlock;

    public VerticalCorner(Props props) {
        super(props.toProperties());
        this.setDefaultState((this.stateContainer.getBaseState()).with(DIRECTION, Direction.NORTH).with(WATERLOGGED, false));
        this.fullBlock = props.getParent().getBlock();
    }

    @Override
    public VoxelShape getShape(BlockState state) {
        switch (state.get(DIRECTION)) {
            case NORTH:
            default:
                return NORTH_SHAPE[state.get(LAYERS) -1];
            case SOUTH:
                return SOUTH_SHAPE[state.get(LAYERS) -1];
            case WEST:
                return WEST_SHAPE[state.get(LAYERS) -1];
            case EAST:
                return EAST_SHAPE[state.get(LAYERS) -1];
        }
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext context) {
        int i = state.get(LAYERS);
        int toggle = Caps.forPlayer(context, Capabilities.TOGGLE, Toggle::getIndex, -1);
        if (toggle == 0 || toggle == 3 || toggle == 6 || toggle == 7) {
            if (context.getItem().getItem() == this.asItem() && i <= 3) {
                if (context.replacingClickedOnBlock()) {
                    return context.getFace() == state.get(DIRECTION);
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockstate = context.getWorld().getBlockState(context.getPos());
        int toggle = Caps.forPlayer(context, Capabilities.TOGGLE, Toggle::getIndex, -1);
        if (toggle == 1 || toggle == 4) {
            return super.getStateForPlacement(context).with(LAYERS, 2);
        }  else if (toggle == 2 || toggle == 5) {
            return super.getStateForPlacement(context).with(LAYERS, 3);
        }
        if (blockstate.getBlock() == this) {
            int i = blockstate.get(LAYERS);
            if (i == 3) {
                return fullBlock.getDefaultState();
            }
            return blockstate.with(LAYERS, Math.min(3, i + 1));
        } else {
            return super.getStateForPlacement(context);
        }
    }

    @Override
    protected void addProperties(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LAYERS);
    }
}
