package com.conquestreforged.core.block;

import com.conquestreforged.core.block.builder.Props;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import java.util.Random;

public class BeanPole extends Crops {

    public BeanPole(Props props) {
        super(props);
    }

    @Override
    public void tick(BlockState state, World world, BlockPos pos, Random rand) {
        super.tick(state, world, pos, rand);

        if (world.isAreaLoaded(pos, 1)) {
            if (world.getLightSubtracted(pos.up(), 0) >= 9) {
                int i = this.getAge(state);

                if (i < this.getMaxAge()) {
                    int height;

                    for (height = 1; world.getBlockState(pos.down(height)).getBlock() == this; ++height) {
                    }

                    if (height < 3) {

                        float f = getGrowthChance(this, world, pos);

                        if (ForgeHooks.onCropsGrowPre(world, pos, state, rand.nextInt((int) (25.0F / f) + 1) == 0)) {
                            if ((world.getBlockState(pos.down()).getMaterial() != Material.PLANTS) || (world.getBlockState(pos.down()).get(this.getAgeProperty()) > i)) {
                                world.setBlockState(pos, this.withAge(i + 1), 2);
                                //if (world.getBlockState(pos.up()).getBlock() == ExampleMod.wooden_pole) {
                                //    world.setBlockState(pos.up(), this.getDefaultState());
                                //}
                                ForgeHooks.onCropsGrowPost(world, pos, state);
                            }
                        }
                    }
                }
            }
        }
    }
}
