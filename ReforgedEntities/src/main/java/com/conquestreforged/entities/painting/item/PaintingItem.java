package com.conquestreforged.entities.painting.item;

import com.conquestreforged.entities.ModEntities;
import com.conquestreforged.entities.painting.Proxy;
import com.conquestreforged.entities.painting.art.Art;
import com.conquestreforged.entities.painting.entity.ArtType;
import com.conquestreforged.entities.painting.entity.PaintingEntity;
import com.conquestreforged.entities.painting.entity.TextureType;
import net.minecraft.entity.item.HangingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @author dags <dags@dags.me>
 */
public class PaintingItem extends Item {

    public PaintingItem() {
        super(new Item.Properties());
        setRegistryName("conquest", "painting");
    }

    public PaintingItem(String name) {
        super(new Item.Properties());
        setRegistryName("conquest", name);

    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (getClass() == PaintingItem.class) {
            if (getGroup() == group || group == ItemGroup.SEARCH) {
                TextureType.getIds().distinct().sorted().forEach(name -> {
                    String type = TextureType.fromName(name).getName();
                    ItemStack stack = createStack(type, ArtType.A1x1_0.shapeId);
                    items.add(stack);
                });
            }
        }
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        CompoundNBT painting = stack.getTag();
        if (painting == null) {
            return super.getDisplayName(stack);
        }

        CompoundNBT data = painting.getCompound(Art.DATA_TAG);
        String typeName = data.getString(Art.TYPE_TAG);
        String artName = data.getString(Art.ART_TAG);

        TextureType type = TextureType.fromId(typeName);
        String displayName = "";//getUnlocalizedName();

        if (type.isPresent()) {
            // mod
            displayName = type.getDisplayName();
            ArtType art = ArtType.fromName(artName);
            if (art != null) {
                displayName = displayName + " " + art.getDisplayName(type.getUnlocalizedName());
            }
        } else if (!artName.isEmpty()) {
            // vanilla
            displayName = artName;
        }

        return new StringTextComponent(displayName);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        if (world.isRemote) {
            ItemStack stack = player.getHeldItemMainhand();
            String name = "";
            String artName = "";
            CompoundNBT data = stack.getTag();
            if (data != null) {
                CompoundNBT painting = data.getCompound(Art.DATA_TAG);
                name = painting.getString(Art.TYPE_TAG);
                artName = painting.getString(Art.ART_TAG);
            }
            Proxy.get().handlePaintingUse(stack, name, artName);
        }
        return super.onItemRightClick(world, player, hand);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        if (player == null) {
            return ActionResultType.FAIL;
        }

        World world = context.getWorld();
        Hand hand = context.getHand();
        Direction side = context.getFace();
        if (player.isSneaking()) {
            onItemRightClick(world, player, hand);
            return ActionResultType.FAIL;
        }

        ItemStack stack = player.getHeldItem(hand);
        CompoundNBT data = stack.getTag();
        if (data == null) {
            return ActionResultType.FAIL;
        }

        CompoundNBT paint = data.getCompound(Art.DATA_TAG);
        String paintType = paint.getString(Art.TYPE_TAG);
        String paintArt = paint.getString(Art.ART_TAG);
        if (paintType.isEmpty() || paintArt.isEmpty()) {
            return ActionResultType.FAIL;
        }

        if (side != Direction.DOWN && side != Direction.UP) {
            BlockPos pos = context.getPos().offset(side);

            HangingEntity painting = createEntity(world, pos, side, paintType, paintArt);
            if (painting == null) {
                return ActionResultType.FAIL;
            }

            if (!world.isRemote) {
                world.addEntity(painting);
                painting.playPlaceSound();
            }

            stack.shrink(1);

            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }

    protected HangingEntity createEntity(World world, BlockPos pos, Direction side, String paintType, String paintArt) {
        TextureType type = TextureType.fromName(paintType);
        ArtType art = ArtType.fromName(paintArt);
        if (!type.isPresent() || art == null) {
            return null;
        }
        PaintingEntity painting = new PaintingEntity(ModEntities.PAINTING, world);
        painting.setType(type);
        painting.setArt(art);
        painting.place(pos, side);
        return painting;
    }

    public static ItemStack createStack(String type, String art) {
        Item item;

        if (type.equalsIgnoreCase("Vanilla")) {
            item = ForgeRegistries.ITEMS.getValue(new ResourceLocation("conquest:vanilla_painting"));
        } else {
            item = ForgeRegistries.ITEMS.getValue(new ResourceLocation("conquest:painting"));
        }

        if (item == null) {
            return ItemStack.EMPTY;
        }

        CompoundNBT painting = new CompoundNBT();
        painting.putString(Art.TYPE_TAG, type);
        painting.putString(Art.ART_TAG, art);

        CompoundNBT data = new CompoundNBT();
        data.put(Art.DATA_TAG, painting);

        ItemStack stack = new ItemStack(item, 1);
        stack.setTag(data);

        return stack;
    }
}
