package com.conquestreforged.core.client.input;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.LinkedList;
import java.util.List;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class Bindings {

    private static final List<EventBinding> bindings = new LinkedList<>();

    public static KeyBinding create(String description, String input, String category) {
        return new KeyBinding(description, getInputId(input), category);
    }

    public static KeyBinding create(String description, String input, String category, BindListener listener) {
        EventBinding binding = new EventBinding(description, getInputId(input), category);
        binding.listen(listener);
        bindings.add(binding);
        return binding;
    }

    public static int getInputId(String inputName) {
        InputMappings.Input input = InputMappings.getInputByName(inputName);
        return input.getKeyCode();
    }

    @SubscribeEvent
    public static void tick(TickEvent.ClientTickEvent event) {
        for (EventBinding binding : bindings) {
            if (binding.checkPressed()) {
                return;
            }
            binding.checkHeld();
        }
    }
}
