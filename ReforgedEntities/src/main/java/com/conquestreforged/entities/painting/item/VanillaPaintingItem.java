package com.conquestreforged.entities.painting.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.HangingEntity;
import net.minecraft.entity.item.PaintingEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @author dags <dags@dags.me>
 */
public class VanillaPaintingItem extends PaintingItem {

    public VanillaPaintingItem() {
        super("vanilla_painting");
    }

    @Override
    protected HangingEntity createEntity(World world, BlockPos pos, Direction side, String paintType, String paintArt) {
        return new PaintingEntity(EntityType.PAINTING, world) {{
            hangingPosition = pos;
            facingDirection = side;
            art = ForgeRegistries.PAINTING_TYPES.getValue(new ResourceLocation(paintArt));
            updateFacingWithBoundingBox(side);
        }};
    }
}
