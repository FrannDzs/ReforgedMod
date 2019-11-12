package com.conquestreforged.core.block;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.base.DirectionalShape;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.World;

@Assets(
        state = @State(name = "%s_beam_horizontal", template = "parent_beam_horizontal"),
        item = @Model(name = "item/%s_beam_horizontal", parent = "block/%s_beam_horizontal_ns", template = "item/parent_beam_horizontal"),
        block = {
                @Model(name = "block/%s_beam_horizontal_ne", template = "block/parent_beam_horizontal_ne"),
                @Model(name = "block/%s_beam_horizontal_ns", template = "block/parent_beam_horizontal_ns"),
                @Model(name = "block/%s_beam_horizontal_nse", template = "block/parent_beam_horizontal_nse"),
                @Model(name = "block/%s_beam_horizontal_nsew", template = "block/parent_beam_horizontal_nsew")
        }
)
public class BeamHorizontal extends DirectionalShape {

    public static final IntegerProperty ACTIVATED = IntegerProperty.create("activated", 1, 4);

    //might make more voxelshapes later on...
    private static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public BeamHorizontal(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state) {
        return SHAPE;
    }

    @Override
    protected void addProperties(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ACTIVATED);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        //IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        Direction facing = context.getPlacementHorizontalFacing().getOpposite();

        return super.getStateForPlacement(context)
                .with(DIRECTION, facing)
                .with(ACTIVATED, 1);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!player.abilities.allowEdit) {
            return false;
        } else {
            world.setBlockState(pos, state.cycle(ACTIVATED), 3);
            return true;
        }
    }
}
