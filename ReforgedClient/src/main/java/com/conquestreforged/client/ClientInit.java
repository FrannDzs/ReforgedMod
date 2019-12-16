package com.conquestreforged.client;

import com.conquestreforged.client.palette.PaletteBindListener;
import com.conquestreforged.client.search.SearchBindListener;
import com.conquestreforged.client.texture.GroupManager;
import com.conquestreforged.client.texture.ItemGroup;
import com.conquestreforged.client.texture.TextureGroup;
import com.conquestreforged.client.toggle.ToggleBindListener;
import com.conquestreforged.core.client.input.Bindings;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientInit {

    private static final String category = "key.category.conquest";

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        Bindings.listen("Block Toggle", "key.keyboard.b", category, new ToggleBindListener());

        Bindings.listen("Palette GUI", "key.keyboard.c", category, new PaletteBindListener());

        Bindings.listen("Search", "key.keyboard.v", category, new SearchBindListener());
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class First {

        private static boolean first = true;

        @SubscribeEvent
        public static void tick(TickEvent.ClientTickEvent event) {
            if (!first) {
                return;
            }

            ItemGroup group = GroupManager.getInstance().getGroup(Blocks.COBBLESTONE);
            System.out.println("## Groups: ");
            for (TextureGroup texture : group.getGroups()) {
                System.out.println("#### Texture: " + texture.getName());
                System.out.println("#### Blocks:");
                for (ItemStack stack : texture.getItems()) {
                    System.out.println("   - " + stack);
                }
            }

            first = false;
        }
    }
}
