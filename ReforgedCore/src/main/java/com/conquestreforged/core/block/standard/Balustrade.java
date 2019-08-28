package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.*;
import com.conquestreforged.core.block.properties.Waterloggable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

@Assets(
        state = @State(name = "%s_balustrade", template = "parent_balustrade"),
        item = @Model(name = "item/%s_balustrade", parent = "block/%s_balustrade", template = "item/parent_balustrade"),
        block = {
                @Model(name = "block/%s_balustrade", template = "block/parent_balustrade"),
                @Model(name = "block/%s_balustrade_base", template = "block/parent_balustrade_base"),
        },
        recipe = @Recipe(
                name = "%s_slab",
                template = "acacia_slab",
                ingredients = {
                        @Ingredient(name = "%s", template = "acacia_planks", plural = true)
                }
        )
)
public class Balustrade extends RotatedPillarBlock implements Waterloggable {

    public static final VoxelShape field_196436_c = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D);
    public static final VoxelShape field_196439_y = Block.makeCuboidShape(3.0D, 4.0D, 4.0D, 13.0D, 5.0D, 12.0D);
    public static final VoxelShape field_196440_z = Block.makeCuboidShape(4.0D, 5.0D, 6.0D, 12.0D, 10.0D, 10.0D);
    public static final VoxelShape field_196434_A = Block.makeCuboidShape(0.0D, 10.0D, 3.0D, 16.0D, 16.0D, 13.0D);
    public static final VoxelShape field_196435_B = Block.makeCuboidShape(4.0D, 4.0D, 3.0D, 12.0D, 5.0D, 13.0D);
    public static final VoxelShape field_196437_C = Block.makeCuboidShape(6.0D, 5.0D, 4.0D, 10.0D, 10.0D, 12.0D);
    public static final VoxelShape field_196438_D = Block.makeCuboidShape(3.0D, 10.0D, 0.0D, 13.0D, 16.0D, 16.0D);
    public static final VoxelShape X_AXIS_AABB = VoxelShapes.or(field_196436_c, VoxelShapes.or(field_196439_y, VoxelShapes.or(field_196440_z, field_196434_A)));
    public static final VoxelShape Z_AXIS_AABB = VoxelShapes.or(field_196436_c, VoxelShapes.or(field_196435_B, VoxelShapes.or(field_196437_C, field_196438_D)));

    public static final VoxelShape Y_BASE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D);
    public static final VoxelShape Y_LOWER = Block.makeCuboidShape(3.0D, 4.0D, 3.0D, 13.0D, 5.0D, 13.0D);
    public static final VoxelShape Y_MIDDLE = Block.makeCuboidShape(4.0D, 5.0D, 4.0D, 12.0D, 11.0D, 12.0D);
    public static final VoxelShape Y_TOP = Block.makeCuboidShape(2.0D, 11.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    public static final VoxelShape Y_AXIS_AABB = VoxelShapes.or(Y_BASE, VoxelShapes.or(Y_LOWER, VoxelShapes.or(Y_MIDDLE, Y_TOP)));


    public Balustrade(Properties properties) {
        super(properties);
        this.setDefaultState((this.stateContainer.getBaseState()).with(AXIS, Direction.Axis.Y).with(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(AXIS)) {
            case X:
            default:
                return X_AXIS_AABB;
            case Y:
                return Y_AXIS_AABB;
            case Z:
                return Z_AXIS_AABB;
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(AXIS)) {
            case X:
            default:
                return X_AXIS_AABB;
            case Y:
                return Y_AXIS_AABB;
            case Z:
                return Z_AXIS_AABB;
        }
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        switch (state.get(AXIS)) {
            case X:
            default:
                return X_AXIS_AABB;
            case Y:
                return Y_AXIS_AABB;
            case Z:
                return Z_AXIS_AABB;
        }
    }

    @Override
    public IFluidState getFluidState(BlockState state) {
        return Waterloggable.getFluidState(state);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AXIS, WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        return super.getStateForPlacement(context)
                .with(AXIS, context.getFace().getAxis())
                .with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
    }
}
