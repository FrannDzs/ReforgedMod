package com.conquestreforged.core.block.standard.glass;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.RackHalberds;
import com.conquestreforged.core.block.standard.ArchRound;
import com.conquestreforged.core.block.standard.ArchRoundHalf;
import com.conquestreforged.core.block.standard.Slab;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@Assets(
        state = @State(name = "%s_pane", template = "parent_pane"),
        item = @Model(name = "item/%s_pane", parent = "block/%s_pane_ns", template = "item/parent_pane"),
        block = {
                @Model(name = "block/%s_pane_n", template = "block/parent_flatpane_n"),
                @Model(name = "block/%s_pane_ne", template = "block/parent_flatpane_ne"),
                @Model(name = "block/%s_pane_ns", template = "block/parent_flatpane_ns"),
                @Model(name = "block/%s_pane_nse", template = "block/parent_flatpane_nse"),
                @Model(name = "block/%s_pane_nsew", template = "block/parent_flatpane_nsew"),
                @Model(name = "block/%s_pane_post", template = "block/parent_flatpane_post"),
                @Model(name = "block/%s_pane_n_up", template = "block/parent_flatpane_n_up"),
                @Model(name = "block/%s_pane_ne_up", template = "block/parent_flatpane_ne_up"),
                @Model(name = "block/%s_pane_ns_up", template = "block/parent_flatpane_ns_up"),
                @Model(name = "block/%s_pane_nse_up", template = "block/parent_flatpane_nse_up"),
                @Model(name = "block/%s_pane_nsew_up", template = "block/parent_flatpane_nsew_up"),
                @Model(name = "block/%s_pane_post_up", template = "block/parent_flatpane_post_up")
        }
)
public class Pane extends PaneBlock {

    public static final BooleanProperty UP = BlockStateProperties.UP;

    public Pane(Properties properties) {
        super(properties);
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IBlockReader iblockreader = context.getWorld();
        BlockPos blockpos = context.getPos();
        BlockPos blockposUp = blockpos.up();
        BlockState blockstateUp = iblockreader.getBlockState(blockposUp);
        return super.getStateForPlacement(context).with(UP, this.canAttachAbove(blockstateUp));
    }

    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos).with(UP, canAttachAbove(worldIn, currentPos.up()));
    }

    public final boolean canAttachAbove(BlockState state) {
        Block block = state.getBlock();
        return (block instanceof StairsBlock || block instanceof Slab || block instanceof SlabBlock || block instanceof ArchRound || block instanceof ArchRoundHalf);
    }

    public final boolean canAttachAbove(IWorld worldIn, BlockPos pos) {
        BlockState BlockState = worldIn.getBlockState(pos);
        Block block = BlockState.getBlock();
        return (block instanceof StairsBlock || block instanceof Slab || block instanceof SlabBlock || block instanceof ArchRound || block instanceof ArchRoundHalf);
    }

    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, WEST, SOUTH, UP, WATERLOGGED);
    }

}
