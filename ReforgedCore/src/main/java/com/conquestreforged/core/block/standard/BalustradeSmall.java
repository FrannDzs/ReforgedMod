package com.conquestreforged.core.block.standard;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.LeadItem;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

@Assets(
        state = @State(name = "%s_small_balustrade", template = "parent_small_balustrade"),
        item = @Model(name = "item/%s_small_balustrade", parent = "block/%s_small_balustrade_inventory", template = "item/acacia_fence"),
        block = {
                @Model(name = "block/%s_small_balustrade_post", template = "block/parent_small_balustrade_post"),
                @Model(name = "block/%s_small_balustrade_n", template = "block/parent_small_balustrade_n"),
                @Model(name = "block/%s_small_balustrade_ne", template = "block/parent_small_balustrade_ne"),
                @Model(name = "block/%s_small_balustrade_ns", template = "block/parent_small_balustrade_ns"),
                @Model(name = "block/%s_small_balustrade_nse", template = "block/parent_small_balustrade_nse"),
                @Model(name = "block/%s_small_balustrade_nsew", template = "block/parent_small_balustrade_nsew"),
                @Model(name = "block/%s_small_balustrade_inventory", template = "block/parent_small_balustrade_inventory"),
        }
)
public class BalustradeSmall extends FenceBlock {

    public BalustradeSmall(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        return false;
    }

    @Override
    public boolean func_220111_a(BlockState p_220111_1_, boolean p_220111_2_, Direction p_220111_3_) {
        Block block = p_220111_1_.getBlock();
        boolean flag = block instanceof BalustradeSmall && p_220111_1_.getMaterial() == this.material;
        boolean flag1 = block instanceof FenceGateBlock && FenceGateBlock.isParallel(p_220111_1_, p_220111_3_);
        return !cannotAttach(block) && p_220111_2_ || flag || flag1;
    }
}
