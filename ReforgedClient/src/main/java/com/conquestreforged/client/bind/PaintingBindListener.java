package com.conquestreforged.client.bind;

import com.conquestreforged.client.gui.painting.PaintingScreen;
import com.conquestreforged.core.client.input.BindEvent;
import com.conquestreforged.core.client.input.BindListener;
import com.conquestreforged.entities.painting.TextureType;
import com.conquestreforged.entities.painting.art.Art;
import com.conquestreforged.entities.painting.art.ArtType;
import com.conquestreforged.items.item.PaintingItem;
import com.conquestreforged.items.item.VanillaPaintingItem;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class PaintingBindListener implements BindListener {
    @Override
    public void onPress(BindEvent e) {
        if (!e.inGame || e.inGui || !e.player.isPresent()) {
            return;
        }

        e.player.map(PlayerEntity::getHeldItemMainhand).ifPresent(stack -> {
            if (stack.getItem() instanceof VanillaPaintingItem && stack.getTag() != null) {
                CompoundNBT painting = stack.getTag().getCompound(Art.DATA_TAG);
                String paintArt = painting.getString(Art.ART_TAG);
                PaintingScreen screen = new PaintingScreen(stack, ForgeRegistries.PAINTING_TYPES.getValue(new ResourceLocation(paintArt)));
                Minecraft.getInstance().displayGuiScreen(screen);
                return;
            }

            if (stack.getItem() instanceof PaintingItem && stack.getTag() != null) {
                CompoundNBT painting = stack.getTag().getCompound(Art.DATA_TAG);
                String paintType = painting.getString(Art.TYPE_TAG);
                String paintArt = painting.getString(Art.ART_TAG);
                PaintingScreen screen = new PaintingScreen(stack, TextureType.fromId(paintType), ArtType.fromName(paintArt));
                Minecraft.getInstance().displayGuiScreen(screen);
                return;
            }

            if (stack.getItem() == Items.PAINTING) {
                PaintingScreen screen = new PaintingScreen(stack, PaintingType.ALBAN);
                Minecraft.getInstance().displayGuiScreen(screen);
            }
        });
    }
}
