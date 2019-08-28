package com.conquestreforged.core.block;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.base.DirectionalShape;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.World;

@Assets(
        state = @State(name = "%s_beam", template = "parent_beam"),
        item = @Model(name = "item/%s_beam", parent = "block/%s_beam_support", template = "item/parent_beam_vertical"),
        block = {
                @Model(name = "block/%s_beam_vertical", template = "block/parent_beam_vertical"),
                @Model(name = "block/%s_beam_support", template = "block/parent_beam_support")
        }
)
public class BeamVertical extends DirectionalShape {

    public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");

    private static final VoxelShape SHAPE_NORTH_OFF = Block.makeCuboidShape(0.0D, 0.0D, 5.0D, 4.0D, 16.0D, 11.0D);
    private static final VoxelShape SHAPE_SOUTH_OFF = Block.makeCuboidShape(12.0D, 0.0D, 5.0D, 16.0D, 16.0D, 11.0D);
    private static final VoxelShape SHAPE_WEST_OFF = Block.makeCuboidShape(5.0D, 0.0D, 12.0D, 11.0D, 16.0D, 16.0D);
    private static final VoxelShape SHAPE_EAST_OFF = Block.makeCuboidShape(5.0D, 0.0D, 0.0D, 11.0D, 16.0D, 4.0D);
    private static final VoxelShape SHAPE_NORTH_ON = Block.makeCuboidShape(0.0D, 0.0D, 5.0D, 1.0D, 16.0D, 11.0D);
    private static final VoxelShape SHAPE_SOUTH_ON = Block.makeCuboidShape(0.0D, 0.0D, 5.0D, 1.0D, 16.0D, 11.0D);
    private static final VoxelShape SHAPE_WEST_ON = Block.makeCuboidShape(5.0D, 0.0D, 0.0D, 11.0D, 16.0D, 16.0D);
    private static final VoxelShape SHAPE_EAST_ON = Block.makeCuboidShape(5.0D, 0.0D, 0.0D, 11.0D, 16.0D, 16.0D);

    public BeamVertical(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state) {
        if (state.get(ACTIVATED)) {
            switch (state.get(DIRECTION)) {
                case NORTH:
                default:
                    return SHAPE_NORTH_OFF;
                case SOUTH:
                    return SHAPE_SOUTH_OFF;
                case WEST:
                    return SHAPE_EAST_OFF;
                case EAST:
                    return SHAPE_WEST_OFF;
            }
        } else {
            switch (state.get(DIRECTION)) {
                case NORTH:
                default:
                    return SHAPE_NORTH_ON;
                case SOUTH:
                    return SHAPE_SOUTH_ON;
                case WEST:
                    return SHAPE_EAST_ON;
                case EAST:
                    return SHAPE_WEST_ON;
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return super.getStateForPlacement(context).with(ACTIVATED, false);
    }

    @Override
    protected void addProperties(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ACTIVATED);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (state.get(ACTIVATED)) {
            state = state.with(ACTIVATED, Boolean.FALSE);
            worldIn.setBlockState(pos, state, 10);
        } else {
            state = state.with(ACTIVATED, Boolean.TRUE);
            worldIn.setBlockState(pos, state, 10);
        }

        worldIn.playEvent(player, state.get(ACTIVATED) ? 1008 : 1014, pos, 0);
        return true;
    }
}
