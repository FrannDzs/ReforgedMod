package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.base.WaterloggedShape;
import com.conquestreforged.core.block.builder.Props;
import com.conquestreforged.core.block.playertoggle.IToggle;
import com.conquestreforged.core.block.playertoggle.ToggleProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.Half;
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
        return SHAPE[state.get(LAYERS) -1];
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        ItemStack item = useContext.getItem();
        PlayerEntity playerEntity = useContext.getPlayer();
        IToggle cap = playerEntity.getCapability(ToggleProvider.PLAYER_TOGGLE).orElseThrow(IllegalAccessError::new);
        int togglenumber = cap.getToggle();
        if (togglenumber == 0 || togglenumber == 3 || togglenumber == 6 || togglenumber == 7) {
            if (item.getItem() == this.asItem()) {
                if (useContext.replacingClickedOnBlock()) {
                    Direction facing = useContext.getFace();
                    return facing != Direction.UP || facing != Direction.UP;
                }
                } else {
                    return true;
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
