package com.conquestreforged.entities.painting.client;

import com.conquestreforged.core.util.RegUtil;
import com.conquestreforged.entities.ModItems;
import com.conquestreforged.entities.painting.CommonProxy;
import com.conquestreforged.entities.painting.NetworkHandler;
import com.conquestreforged.entities.painting.Proxy;
import com.conquestreforged.entities.painting.client.gui.GuiPainting;
import com.conquestreforged.entities.painting.client.render.PaintingRenderer;
import com.conquestreforged.entities.painting.entity.ArtType;
import com.conquestreforged.entities.painting.entity.PaintingEntity;
import com.conquestreforged.entities.painting.entity.TextureType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CCustomPayloadPacket;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientProxy extends CommonProxy {

    @Override
    public void sendSyncPacket(PacketBuffer buffer) {
        CCustomPayloadPacket packet = new CCustomPayloadPacket(NetworkHandler.SYNC_CHANNEL, buffer);
        Minecraft.getInstance().getConnection().getNetworkManager().sendPacket(packet);
    }

    @Override
    public void handlePaintingUse(ItemStack stack, String name, String artName) {
        if (stack.getItem() == ModItems.vanillaPainting) {
            if (artName.isEmpty()) {
                artName = PaintingType.ALBAN.toString();
            }
            GuiPainting gui = new GuiPainting(stack, RegUtil.art(artName));
            Minecraft.getInstance().displayGuiScreen(gui);
        } else {
            TextureType type = TextureType.fromId(name);
            ArtType art = ArtType.fromName(artName);
            if (!type.isPresent() || art == null) {
                return;
            }

            GuiPainting gui = new GuiPainting(stack, type, art);
            Minecraft.getInstance().displayGuiScreen(gui);
        }
    }

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        Proxy.instance.set(new ClientProxy());
    }

    @SubscribeEvent
    public static void registerRenders(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(PaintingEntity.class, PaintingRenderer::new);
        registerModel(ModItems.conquestPainting, "conquest:painting");
        registerModel(ModItems.vanillaPainting, "minecraft:painting");
    }

    private static void registerModel(Item item, String id) {
        ModelResourceLocation modelLocation = new ModelResourceLocation(id, "inventory");
        Minecraft.getInstance().getItemRenderer().getItemModelMesher().register(item, modelLocation);
    }
}
