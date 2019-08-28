package com.conquestreforged.entities.painting.entity;

import net.minecraft.client.resources.I18n;

/**
 * @author dags <dags@dags.me>
 */
public interface Translateable {

    String getName();

    String getUnlocalizedName();

    String getUnlocalizedName(String parent);

    default String getDisplayName() {
        String lookup = getUnlocalizedName();
        String translation = I18n.format(lookup);
        if (translation.equals(lookup)) {
            return getName();
        }
        return translation;
    }

    default String getDisplayName(String parent) {
        String lookup = getUnlocalizedName(parent);
        String translation = I18n.format(lookup);
        if (translation.equals(lookup)) {
            return getName();
        }
        return translation;
    }
}
