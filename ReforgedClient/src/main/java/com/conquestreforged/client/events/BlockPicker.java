package com.conquestreforged.client.events;

import com.conquestreforged.core.item.ItemUtils;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BlockPicker {

    @SubscribeEvent
    public static void onPick(InputEvent.ClickInputEvent event) {
        if (event.getKeyBinding() != Minecraft.getInstance().options.keyPickItem) {
            return;
        }

        if (!Screen.hasControlDown()) {
            return;
        }

        PlayerEntity player = Minecraft.getInstance().player;
        if (player == null || !player.abilities.instabuild) {
            return;
        }

        RayTraceResult result = Minecraft.getInstance().hitResult;
        if (result == null || result.getType() != RayTraceResult.Type.BLOCK) {
            return;
        }

        BlockPos pos = ((BlockRayTraceResult) result).getBlockPos();
        BlockState state = player.level.getBlockState(pos);
        if (state.hasTileEntity()) {
            return;
        }

        ItemStack stack = ItemUtils.fromState(state);
        player.inventory.setPickedItem(stack);
        Minecraft.getInstance().gameMode.handleCreativeModeItemAdd(player.getItemInHand(Hand.MAIN_HAND), 36 + player.inventory.selected);
        event.setCanceled(true);
    }
}
