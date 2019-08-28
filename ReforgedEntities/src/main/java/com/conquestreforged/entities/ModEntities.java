package com.conquestreforged.entities;

import com.conquestreforged.entities.painting.entity.PaintingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;

public class ModEntities {

    public static final EntityType<PaintingEntity> PAINTING = build(
            "conquest:painting",
            EntityType.Builder.<PaintingEntity>create(PaintingEntity::new, EntityClassification.MISC)
                    .size(0.5F, 0.5F)
    );

    private static <T extends Entity> EntityType<T> build(String name, EntityType.Builder<T> builder) {
        EntityType<T> type = builder.build(name);
        type.setRegistryName(name);
        return type;
    }
}
