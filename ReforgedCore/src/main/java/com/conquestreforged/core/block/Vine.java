package com.conquestreforged.core.block;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import net.minecraft.block.BlockState;
import net.minecraft.block.VineBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

@Assets(
        state = @State(name = "%s", template = "vine"),
        item = @Model(name = "item/%s", parent = "block/%s_pane_ns", template = "item/parent_pane"),
        block = {
                @Model(name = "block/%s_vine_1", template = "block/vine_1"),
                @Model(name = "block/%s_vine_1u", template = "block/vine_1u"),
                @Model(name = "block/%s_vine_2", template = "block/vine_2"),
                @Model(name = "block/%s_vine_2u", template = "block/vine_2u"),
                @Model(name = "block/%s_vine_2_opposite", template = "block/vine_2_opposite"),
                @Model(name = "block/%s_vine_2u_opposite", template = "block/vine_2u_opposite"),
                @Model(name = "block/%s_vine_3", template = "block/vine_3"),
                @Model(name = "block/%s_vine_3u", template = "block/vine_3u"),
                @Model(name = "block/%s_vine_4", template = "block/vine_4"),
                @Model(name = "block/%s_vine_4u", template = "block/vine_4u"),
                @Model(name = "block/%s_vine_u", template = "block/vine_u"),
                @Model(name = "block/%s_pane_ns", template = "block/parent_flatpane_ns")
        }
)
public class Vine extends VineBlock {

    public Vine(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader reader, BlockPos pos) {
        return true;
    }
}
