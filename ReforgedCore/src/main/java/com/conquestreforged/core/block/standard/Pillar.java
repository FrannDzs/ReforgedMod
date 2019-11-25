package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.base.WaterloggedShape;
import com.conquestreforged.core.block.builder.Props;
import com.conquestreforged.core.capability.utils.Caps;
import com.conquestreforged.core.capability.Capabilities;
import com.conquestreforged.core.capability.toggle.Toggle;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShape;


@Assets(
        state = @State(name = "%s_pillar", template = "parent_pillar"),
        item = @Model(name = "item/%s_pillar", parent = "block/%s_pillar_4", template = "item/dragon_egg"),
        block = {
                @Model(name = "block/%s_pillar_2", template = "block/parent_pillar_2"),
                @Model(name = "block/%s_pillar_4", template = "block/parent_pillar_4"),
                @Model(name = "block/%s_pillar_6", template = "block/parent_pillar_6"),
        }
)
public class Pillar extends WaterloggedShape {

    public static final IntegerProperty LAYERS = IntegerProperty.create("layer", 1, 3);
    protected static final VoxelShape[] SHAPE = new VoxelShape[]{Block.makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D), Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D), Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D)};

    private Block fullBlock;

    public Pillar(Props properties) {
        super(properties.toProperties());
        setDefaultState((stateContainer.getBaseState()).with(WATERLOGGED, false));
        this.fullBlock = properties.getParent().getBlock();
    }

    @Override
    public VoxelShape getShape(BlockState state) {
        return SHAPE[state.get(LAYERS) - 1];
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext context) {
        ItemStack item = context.getItem();
        int toggle = Caps.forPlayer(context, Capabilities.TOGGLE, Toggle::getIndex, -1);
        if (toggle == 0 || toggle == 3 || toggle == 6 || toggle == 7) {
            if (item.getItem() == this.asItem()) {
                if (context.replacingClickedOnBlock()) {
                    Direction facing = context.getFace();

                    // TODO what!?
                    return facing != Direction.UP || facing != Direction.UP;
                }
            } else {
                return true;
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
        } else if (toggle == 2 || toggle == 5) {
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
