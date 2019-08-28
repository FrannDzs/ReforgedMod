package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;

/**
 * Orientable cube with the following faces:
 * - Front -> North
 * - Side  -> East/South/West
 * - Top   -> Top/Bottom
 */
@Assets(
        state = @State(name = "%s_directional", template = "anvil", plural = true),
        item = @Model(name = "item/%s_directional", parent = "block/%s", template = "item/acacia_planks"),
        block = @Model(name = "block/%s_directional", template = "block/orientable")
)
public class CubeDirectional extends Block {

    private static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    public CubeDirectional(Block.Properties properties) {
        super(properties);
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
