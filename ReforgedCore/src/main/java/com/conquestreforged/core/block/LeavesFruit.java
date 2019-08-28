package com.conquestreforged.core.block;

import com.conquestreforged.core.block.builder.Props;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class LeavesFruit extends CropsBlock implements IShearable {

    public static final IntegerProperty DISTANCE = BlockStateProperties.DISTANCE_1_7;
    public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;
    private final IItemProvider fruit;

    public LeavesFruit(Props props) {
        super(props.toProperties());
        this.fruit = props.get("fruit", IItemProvider.class);
        this.setDefaultState(this.stateContainer.getBaseState().with(DISTANCE, 7).with(PERSISTENT, false).with(AGE, 0));
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return state.get(DISTANCE) == 7 && !state.get(PERSISTENT);
    }

    @Override
    public void randomTick(BlockState state, World world, BlockPos pos, Random rand) {
        if (!state.get(PERSISTENT) && state.get(DISTANCE) == 7) {
//            state.dropBlockAsItem(world, pos, 0);
            world.removeBlock(pos, true);
        }
    }

    @Override
    public void tick(BlockState state, World world, BlockPos pos, Random rand) {
        world.setBlockState(pos, updateDistance(state, world, pos), 3);
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState state1, IWorld world, BlockPos pos, BlockPos pos1) {
        int i = getDistance(state1) + 1;
        if (i != 1 || state.get(DISTANCE) != i) {
            world.getPendingBlockTicks().scheduleTick(pos, this, 1);
        }

        return state;
    }

    private static BlockState updateDistance(BlockState state, IWorld world, BlockPos pos) {
        int i = 7;
        BlockPos.PooledMutableBlockPos position = BlockPos.PooledMutableBlockPos.retain();
        Throwable var5 = null;

        try {
            Direction[] var6 = Direction.values();
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                Direction Direction = var6[var8];
                position.setPos(pos).move(Direction);
                i = Math.min(i, getDistance(world.getBlockState(position)) + 1);
                if (i == 1) {
                    break;
                }
            }
        } catch (Throwable var17) {
            var5 = var17;
            throw var17;
        } finally {
            if (position != null) {
                if (var5 != null) {
                    try {
                        position.close();
                    } catch (Throwable var16) {
                        var5.addSuppressed(var16);
                    }
                } else {
                    position.close();
                }
            }
        }
        return state.with(DISTANCE, i);
    }

    private static int getDistance(BlockState state) {
        if (BlockTags.LOGS.contains(state.getBlock())) {
            return 0;
        } else {
            return BlockTags.LEAVES.contains(state.getBlock()) ? state.get(DISTANCE) : 7;
        }
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> stateList) {
        stateList.add(DISTANCE, PERSISTENT, AGE);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return updateDistance(this.getDefaultState().with(PERSISTENT, true), context.getWorld(), context.getPos());
    }

    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack itemStack, IWorld world, BlockPos pos, int num) {
        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
        return Arrays.asList(new ItemStack(this));
    }

    @Override
    public boolean isLadder(BlockState state, IWorldReader reader, BlockPos pos, LivingEntity entity) {
        return true;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        entity.setMotion(entity.getMotion().mul(0.4D, 1.0D, 0.4D));
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
            worldIn.addEntity(entityitem);
        }
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.NONE;
    }
}
