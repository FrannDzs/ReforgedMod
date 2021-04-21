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
    private boolean hasRenderedIntro = false;
    private boolean hasRenderedDependency = false;

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
            List<Dependency> missing = dependencies.getMissingDependencies();
            DependencyScreen screen2 = new DependencyScreen(event.getGui(), section, missing);
            IntroScreen screen1 = new IntroScreen(screen2, section);
            IntroScreen screen3 = new IntroScreen(event.getGui(), section);

            if ((section.getOrElse("ignore_dependencies", false) && section.getOrElse("ignore_intro", false))
                    || missing.isEmpty() && Tutorials.intro
                    || (Tutorials.dependencies && Tutorials.intro)
            ) {
                MinecraftForge.EVENT_BUS.unregister(this);
                return;
            }

            if (!Tutorials.dependencies) {
                if (section.getOrElse("ignore_dependencies", false) || missing.isEmpty()) {
                    Minecraft.getInstance().displayGuiScreen(screen3);
                    if (Tutorials.intro) {
                        MinecraftForge.EVENT_BUS.unregister(this);
                    }
                } else if (section.getOrElse("ignore_intro", false)) {
                    Minecraft.getInstance().displayGuiScreen(screen2);
                } else {
                    Minecraft.getInstance().displayGuiScreen(screen1);
                }
            }
        }
    }
}
