package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.base.WaterloggedShape;
import com.conquestreforged.core.block.properties.Waterloggable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SixWayBlock;
import net.minecraft.block.SnowBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

@Assets(
        state = @State(name = "%s_rocks", template = "parent_rocks"),
        item = @Model(name = "item/%s_rocks", parent = "block/%s_rocks_1", template = "item/parent_round_arch"),
        block = {
                @Model(name = "block/%s_rocks_1", template = "block/parent_rocks_1"),
                @Model(name = "block/%s_rocks_2", template = "block/parent_rocks_2"),
                @Model(name = "block/%s_rocks_2_ne", template = "block/parent_rocks_2_ne"),
                @Model(name = "block/%s_rocks_2_none", template = "block/parent_rocks_2_none"),
                @Model(name = "block/%s_rocks_2_nse", template = "block/parent_rocks_2_nse"),
                @Model(name = "block/%s_rocks_3", template = "block/parent_rocks_3"),
                @Model(name = "block/%s_rocks_3_n", template = "block/parent_rocks_3_n"),
                @Model(name = "block/%s_rocks_3_ne", template = "block/parent_rocks_3_ne"),
                @Model(name = "block/%s_rocks_3_none", template = "block/parent_rocks_3_none"),
                @Model(name = "block/%s_rocks_4", template = "block/parent_rocks_4"),
                @Model(name = "block/%s_rocks_5", template = "block/parent_rocks_5"),
                @Model(name = "block/%s_rocks_5_ne", template = "block/parent_rocks_5_ne"),
                @Model(name = "block/%s_rocks_5_none", template = "block/parent_rocks_5_none"),
                @Model(name = "block/%s_rocks_5_nse", template = "block/parent_rocks_5_nse"),
                @Model(name = "block/%s_rocks_6", template = "block/parent_rocks_6"),
                @Model(name = "block/%s_rocks_6_n", template = "block/parent_rocks_6_n"),
                @Model(name = "block/%s_rocks_6_ne", template = "block/parent_rocks_6_ne"),
                @Model(name = "block/%s_rocks_6_nse", template = "block/parent_rocks_6_nse"),
                @Model(name = "block/%s_rocks_7", template = "block/parent_rocks_7"),
                @Model(name = "block/%s_rocks_7_n", template = "block/parent_rocks_7_n"),
                @Model(name = "block/%s_rocks_7_ne", template = "block/parent_rocks_7_ne"),
                @Model(name = "block/%s_rocks_7_none", template = "block/parent_rocks_7_none"),
                @Model(name = "block/%s_rocks_7_ns", template = "block/parent_rocks_7_ns"),
                @Model(name = "block/%s_rocks_7_nse", template = "block/parent_rocks_7_nse"),
                @Model(name = "block/%s_rocks_8", template = "block/parent_rocks_8"),
                @Model(name = "block/%s_rocks_8_n", template = "block/parent_rocks_8_n"),
                @Model(name = "block/%s_rocks_8_ne", template = "block/parent_rocks_8_ne"),
                @Model(name = "block/%s_rocks_8_none", template = "block/parent_rocks_8_none")
        }
)
public class Rocks extends WaterloggedShape {

    public static final BooleanProperty NORTH = SixWayBlock.NORTH;
    public static final BooleanProperty EAST = SixWayBlock.EAST;
    public static final BooleanProperty SOUTH = SixWayBlock.SOUTH;
    public static final BooleanProperty WEST = SixWayBlock.WEST;
    private static final VoxelShape SHAPE = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 15.0D, 15.0D);

    public Rocks(Properties properties) {
        super(properties);
        this.setDefaultState((this.stateContainer.getBaseState()).with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader reader, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return stateIn.with(NORTH, canConnectTo(worldIn, currentPos.north().down())).with(EAST, canConnectTo(worldIn, currentPos.east().down())).with(SOUTH, canConnectTo(worldIn, currentPos.south().down())).with(WEST, canConnectTo(worldIn, currentPos.west().down()));
    }

    private boolean canConnectTo(IWorld worldIn, BlockPos pos) {
        BlockState blockstate = worldIn.getBlockState(pos);
        return blockstate.isSolid();
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());

        BlockPos pos = context.getPos();
        IBlockReader iblockreader = context.getWorld();
        BlockState north = iblockreader.getBlockState(pos.north().down());
        BlockState east = iblockreader.getBlockState(pos.east().down());
        BlockState south = iblockreader.getBlockState(pos.south().down());
        BlockState west = iblockreader.getBlockState(pos.west().down());

        return super.getStateForPlacement(context).with(NORTH, north.isSolid()).with(EAST, east.isSolid()).with(SOUTH, south.isSolid()).with(WEST, west.isSolid()).with(WATERLOGGED, Boolean.valueOf(ifluidstate.getFluid() == Fluids.WATER));
    }

    @Override
    public IFluidState getFluidState(BlockState state) {
        return Waterloggable.getFluidState(state);
    }

    @Override
    protected void addProperties(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public Vec3d getOffset(BlockState state, IBlockReader worldIn, BlockPos pos) {
        if (worldIn.getBlockState(pos.down()).getBlock() instanceof SnowBlock) {
            if (worldIn.getBlockState(pos.down()).get(SnowBlock.LAYERS) == 6) {
                return new Vec3d(0D, -0.1D, 0D);
            } else if (worldIn.getBlockState(pos.down()).get(SnowBlock.LAYERS) == 5) {
                return new Vec3d(0D, -0.25D, 0D);
            } else if (worldIn.getBlockState(pos.down()).get(SnowBlock.LAYERS) == 4) {
                return new Vec3d(0D, -0.4D, 0D);
            } else if (worldIn.getBlockState(pos.down()).get(SnowBlock.LAYERS) == 3) {
                return new Vec3d(0D, -0.52D, 0D);
            } else if (worldIn.getBlockState(pos.down()).get(SnowBlock.LAYERS) == 2) {
                return new Vec3d(0D, -0.65D, 0D);
            } else if (worldIn.getBlockState(pos.down()).get(SnowBlock.LAYERS) == 1) {
                return new Vec3d(0D, -0.75D, 0D);
            } else if (worldIn.getBlockState(pos.down()).get(SnowBlock.LAYERS) == 0) {
                return new Vec3d(0D, -0.9D, 0D);
            }
        }
        return Vec3d.ZERO;
    }

}