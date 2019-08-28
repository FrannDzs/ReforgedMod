package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.base.WaterloggedShape;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.shapes.VoxelShape;


@Assets(
        state = @State(name = "%s_pillar", template = "parent_pillar"),
        item = @Model(name = "item/%s_pillar", parent = "block/%s_wall_post", template = "item/dragon_egg"),
        block = {
                @Model(name = "block/%s_wall_post", template = "block/cobblestone_wall_post"),
        }
)
public class Pillar extends WaterloggedShape {

    protected static final VoxelShape SHAPE = Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    public Pillar(Properties properties) {
        super(properties);
        setDefaultState((stateContainer.getBaseState()).with(WATERLOGGED, false));

    }

    @Override
    public VoxelShape getShape(BlockState state) {
        return SHAPE;
    }
}
