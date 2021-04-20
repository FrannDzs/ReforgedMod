package com.conquestreforged.client.tutorial;

import com.conquestreforged.client.gui.dependency.Dependency;
import com.conquestreforged.client.gui.dependency.DependencyList;
import com.conquestreforged.client.gui.dependency.screen.DependencyScreen;
import com.conquestreforged.client.gui.intro.IntroScreen;
import com.conquestreforged.core.config.ConfigBuildEvent;
import com.conquestreforged.core.config.section.ConfigSection;
import com.conquestreforged.core.config.section.ConfigSectionSpec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TutorialRenderEvent {

    private final ConfigSection section;
    private final DependencyList dependencies = DependencyList.load();

    public TutorialRenderEvent(ConfigSection section) {
        this.section = section;
    }

    @SubscribeEvent
    public static void config(ConfigBuildEvent event) {
        try (ConfigSectionSpec spec = event.client("tutorials")) {
            spec.getBuilder().define("ignore_dependencies", false).next();
            spec.getBuilder().define("ignore_intro", false).next();
            MinecraftForge.EVENT_BUS.register(new TutorialRenderEvent(spec.getSection()));
        }
    }

    @SubscribeEvent
    public void render(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof MainMenuScreen) {
            if (Tutorials.dependencies && Tutorials.intro) {
                MinecraftForge.EVENT_BUS.unregister(this);
                return;
            }

            if (section.getOrElse("ignore_dependencies", false) && section.getOrElse("ignore_intro", false)) {
                MinecraftForge.EVENT_BUS.unregister(this);
                return;
            }

            List<Dependency> missing = dependencies.getMissingDependencies();
            if (missing.isEmpty()) {
                return;
            }

            DependencyScreen screen2 = new DependencyScreen(event.getGui(), section, missing);
            Minecraft.getInstance().displayGuiScreen(screen2);

            if (Tutorials.intro) {
                MinecraftForge.EVENT_BUS.unregister(this);
                return;
            }

            if (section.getOrElse("ignore_intro", false)) {
                MinecraftForge.EVENT_BUS.unregister(this);
                return;
            }

            if (Tutorials.dependencies) {
                IntroScreen screen1 = new IntroScreen(event.getGui(), section);
                Minecraft.getInstance().displayGuiScreen(screen1);
            } else {
                IntroScreen screen1 = new IntroScreen(screen2, section);
                Minecraft.getInstance().displayGuiScreen(screen1);
            }
        }
    }
}
