package com.conquestreforged.core.block;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.builder.Props;
import com.conquestreforged.core.block.standard.Layer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Assets(
        state = @State(name = "%s_layer", template = "parent_layer"),
        item = @Model(name = "item/%s_layer", parent = "block/%s_layer_height2", template = "item/snow"),
        block = {
                @Model(name = "block/%s_layer_height2", template = "block/snow_height2"),
                @Model(name = "block/%s_layer_height4", template = "block/snow_height4"),
                @Model(name = "block/%s_layer_height6", template = "block/snow_height6"),
                @Model(name = "block/%s_layer_height8", template = "block/snow_height8"),
                @Model(name = "block/%s_layer_height10", template = "block/snow_height10"),
                @Model(name = "block/%s_layer_height12", template = "block/snow_height12"),
                @Model(name = "block/%s_layer_height14", template = "block/snow_height14")
        }
)
public class DamageBlockLayer extends Layer {

    public DamageBlockLayer(Props properties) {
        super(properties);
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        if (!entityIn.isImmuneToFire() && entityIn instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entityIn)) {
            entityIn.attackEntityFrom(DamageSource.HOT_FLOOR, 1.0F);
        }

        super.onEntityWalk(worldIn, pos, entityIn);
    }
}
