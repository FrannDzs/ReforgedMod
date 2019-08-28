package com.conquestreforged.core.block;

import com.conquestreforged.core.block.standard.VerticalSlab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class TowelRack extends VerticalSlab {

    public static final BooleanProperty CAP = BlockStateProperties.EYE;

    public TowelRack(Properties properties) {
        super(properties);
        this.setDefaultState((this.stateContainer.getBaseState())
                .with(DIRECTION, Direction.NORTH)
                .with(CAP,false));
    }

    @Override
    public VoxelShape getShape(BlockState state) {
        return VoxelShapes.fullCube();
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction facing = context.getPlacementHorizontalFacing().getOpposite();

        return super.getStateForPlacement(context)
                .with(DIRECTION, facing)
                .with(CAP, false);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return true;
        } else {
            state = state.cycle(CAP);
            worldIn.setBlockState(pos, state, 3);
            return true;
        }
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader reader, BlockPos pos) {
        return true;
    }

    @Override
    protected void addProperties(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(CAP);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
