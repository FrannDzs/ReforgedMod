package com.conquestreforged.core.block;

import com.conquestreforged.core.asset.annotation.Assets;
import com.conquestreforged.core.asset.annotation.Model;
import com.conquestreforged.core.asset.annotation.State;
import com.conquestreforged.core.block.builder.Props;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.SnowBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

@Assets(
        state = @State(name = "%s", template = "parent_berry_bush"),
        item = @Model(name = "item/%s", parent = "block/%s_pane_ns", template = "item/parent_round_arch"),
        block = {
                @Model(name = "block/%s_shrub_model_1", template = "block/parent_shrub_model_1"),
                @Model(name = "block/%s_shrub_model_2_a", template = "block/parent_shrub_model_2_a"),
                @Model(name = "block/%s_shrub_model_3", template = "block/parent_shrub_model_3"),
                @Model(name = "block/%s_berry_bush_model_1", template = "block/parent_berry_bush_model_1"),
                @Model(name = "block/%s_berry_bush_model_2_a", template = "block/parent_berry_bush_model_2_a"),
                @Model(name = "block/%s_berry_bush_model_3", template = "block/parent_berry_bush_model_3"),
                @Model(name = "block/%s_pane_ns", template = "block/parent_flatpane_ns"),
        }
)
public class BerryBush extends CropsBlock {

    private final IItemProvider fruit;

    public BerryBush(Props props) {
        super(props.toProperties());
        this.fruit = props.get("fruit", IItemProvider.class);
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader reader, BlockPos pos) {
        return VoxelShapes.fullCube();
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader reader, BlockPos pos) {
        return true;
    }

    @Override
    protected boolean isValidGround(BlockState p_200014_1_, IBlockReader p_200014_2_, BlockPos p_200014_3_) {
        return true;
    }

    @Override
    public ItemStack getItem(IBlockReader reader, BlockPos pos, BlockState state) {
        return new ItemStack(this,1);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (this.isMaxAge(state)) {
            if (worldIn.isRemote) {
                return true;
            } else {
                state = state.with(AGE, 0);
                worldIn.setBlockState(pos, state, 3);
                this.dropFruit(worldIn, pos, state);
                return true;

            }
        }
        return false;
    }

    private void dropFruit(World worldIn, BlockPos pos, BlockState state) {
        if (!worldIn.isRemote) {
            float f = 0.7F;
            double d0 = (double) (worldIn.rand.nextFloat() * 0.7F) + 0.15000000596046448D;
            double d1 = (double) (worldIn.rand.nextFloat() * 0.7F) + 0.06000000238418579D + 0.6D;
            double d2 = (double) (worldIn.rand.nextFloat() * 0.7F) + 0.15000000596046448D;
            ItemStack itemstack1 = new ItemStack(fruit, 1);
            ItemEntity entityitem = new ItemEntity(worldIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, itemstack1);
            entityitem.setDefaultPickupDelay();

            // addEntity == spawnEntitiy
            worldIn.addEntity(entityitem);
        }
    }

    @Override
    public Vec3d getOffset(BlockState state, IBlockReader worldIn, BlockPos pos) {
        long i = MathHelper.getCoordinateRandom(pos.getX(), 0, pos.getZ());
        double x = ((double)((float)(i & 15L) / 15.0F) - 0.5D) * 0.5D;
        double z = ((double)((float)(i >> 8 & 15L) / 15.0F) - 0.5D) * 0.5D;

        if (worldIn.getBlockState(pos.down()).getBlock() instanceof SnowBlock) {
            if (worldIn.getBlockState(pos.down()).get(SnowBlock.LAYERS) == 6) {
                return new Vec3d(x, -0.1D, z);
            } else if (worldIn.getBlockState(pos.down()).get(SnowBlock.LAYERS) == 5) {
                return new Vec3d(x, -0.2D, z);
            } else if (worldIn.getBlockState(pos.down()).get(SnowBlock.LAYERS) == 4) {
                return new Vec3d(x, -0.3D, z);
            } else if (worldIn.getBlockState(pos.down()).get(SnowBlock.LAYERS) == 3) {
                return new Vec3d(x, -0.4D, z);
            } else if (worldIn.getBlockState(pos.down()).get(SnowBlock.LAYERS) == 2) {
                return new Vec3d(x, -0.5D, z);
            } else if (worldIn.getBlockState(pos.down()).get(SnowBlock.LAYERS) == 1) {
                return new Vec3d(x, -0.6D, z);
            } else if (worldIn.getBlockState(pos.down()).get(SnowBlock.LAYERS) == 0) {
                return new Vec3d(x, -0.7D, z);
            }
        }
        return new Vec3d(x, ((double)((float)(i >> 4 & 15L) / 15.0F) - 1.0D) * 0.2D, z);
    }
}
