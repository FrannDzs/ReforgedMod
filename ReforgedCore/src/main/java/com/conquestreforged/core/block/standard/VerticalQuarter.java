package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.base.WaterloggedDirectionalShape;
import com.conquestreforged.core.block.builder.Props;
import com.conquestreforged.core.block.playertoggle.IToggle;
import com.conquestreforged.core.block.playertoggle.ToggleProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

@Assets(
        state = @State(name = "%s_vertical_quarter", template = "parent_vertical_quarter"),
        item = @Model(name = "item/%s_vertical_quarter", parent = "block/%s_vertical_quarter_4", template = "item/parent_vertical_quarter"),
        block = {
                @Model(name = "block/%s_vertical_quarter_2", template = "block/parent_vertical_quarter_2"),
                @Model(name = "block/%s_vertical_quarter_4", template = "block/parent_vertical_quarter_4"),
                @Model(name = "block/%s_vertical_quarter_6", template = "block/parent_vertical_quarter_6"),
        }
)
public class VerticalQuarter extends WaterloggedDirectionalShape {

    public static final IntegerProperty LAYERS = IntegerProperty.create("layer", 1, 3);

    private static final VoxelShape[] NORTH_SHAPE = new VoxelShape[]{Block.makeCuboidShape(12.0D, 0.0D, 12.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D), Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 16.0D, 16.0D, 16.0D)};
    private static final VoxelShape[] SOUTH_SHAPE = new VoxelShape[]{Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 4.0D, 16.0D, 4.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 8.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 12.0D, 16.0D, 12.0D)};
    private static final VoxelShape[] EAST_SHAPE = new VoxelShape[]{Block.makeCuboidShape(0.0D, 0.0D, 12.0D, 4.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 16.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 4.0D, 12.0D, 16.0D, 16.0D)};
    private static final VoxelShape[] WEST_SHAPE = new VoxelShape[]{Block.makeCuboidShape(12.0D, 0.0D, 0.0D, 16.0D, 16.0D, 4.0D), Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D), Block.makeCuboidShape(4.0D, 0.0D, 0.0D, 16.0D, 16.0D, 12.0D)};
    private Block fullBlock;

    public VerticalQuarter(Props props) {
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
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        int i = state.get(LAYERS);
        PlayerEntity playerEntity = useContext.getPlayer();
        IToggle cap = playerEntity.getCapability(ToggleProvider.PLAYER_TOGGLE).orElseThrow(IllegalAccessError::new);
        int togglenumber = cap.getToggle();
        if (togglenumber == 0 || togglenumber == 3 || togglenumber == 6 || togglenumber == 7) {
            if (useContext.getItem().getItem() == this.asItem() && i <= 3) {
                if (useContext.replacingClickedOnBlock()) {
                    return useContext.getFace() == state.get(DIRECTION) || useContext.getFace() == state.get(DIRECTION).rotateYCCW();
                } else {
                    return true;
                }
            }
        } else {
            return false;
        }
        return false;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockstate = context.getWorld().getBlockState(context.getPos());
        PlayerEntity playerEntity = context.getPlayer();
        IToggle cap = playerEntity.getCapability(ToggleProvider.PLAYER_TOGGLE).orElseThrow(IllegalAccessError::new);
        int togglenumber = cap.getToggle();
        if (togglenumber == 1 || togglenumber == 4) {
            return super.getStateForPlacement(context).with(LAYERS, 2);
        }  else if (togglenumber == 2 || togglenumber == 5) {
            return super.getStateForPlacement(context).with(LAYERS, 3);
        }
        if (blockstate.getBlock() == this) {
            int i = blockstate.get(LAYERS);
            if (i == 3) {
                return fullBlock.getDefaultState();
            }
            return blockstate.with(LAYERS, Integer.valueOf(Math.min(3, i + 1)));
        } else {
            return super.getStateForPlacement(context);
        }
    }

    @Override
    protected void addProperties(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LAYERS);
    }
}
