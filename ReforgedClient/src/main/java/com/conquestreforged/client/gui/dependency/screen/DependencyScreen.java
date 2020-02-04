package com.conquestreforged.client.gui.dependency.screen;

import com.conquestreforged.client.gui.dependency.Dependency;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;

public class DependencyScreen extends Screen {

    private final List<Dependency> missing;

    public DependencyScreen(List<Dependency> missing) {
        super(new StringTextComponent("Dependencies"));
        this.missing = missing;
    }
}
