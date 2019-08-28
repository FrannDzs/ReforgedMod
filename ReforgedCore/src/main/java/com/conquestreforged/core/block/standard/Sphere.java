package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.base.WaterloggedShape;
import com.conquestreforged.core.block.properties.SphereShape;
import com.conquestreforged.core.block.properties.Waterloggable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.shapes.VoxelShape;


@Assets(
        state = @State(name = "%s_sphere", template = "parent_sphere"),
        item = @Model(name = "item/%s_sphere", parent = "block/%s_sphere", template = "item/dragon_egg"),
        block = {
                @Model(name = "block/%s_sphere", template = "block/parent_sphere"),
                @Model(name = "block/%s_sphere_dragonegg", template = "block/dragon_egg"),
                @Model(name = "block/%s_sphere_small", template = "block/parent_sphere_small"),
        }
)
public class Sphere extends WaterloggedShape implements Waterloggable {

    private static final VoxelShape SHAPE_SMALL = Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 11.0D, 13.0D);
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    public static final EnumProperty TYPE = EnumProperty.create("type", SphereShape.class);

    public Sphere(Block.Properties properties) {
        super(properties);
        setDefaultState((stateContainer.getBaseState()).with(TYPE, SphereShape.LARGE).with(WATERLOGGED, false));

    }

    @Override
    public VoxelShape getShape(BlockState state) {
        if (state.get(TYPE) == SphereShape.SMALL) {
            return SHAPE_SMALL;
        } else {
            return SHAPE;
        }
    }

    @Override
    protected void addProperties(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(TYPE);
    }
}
