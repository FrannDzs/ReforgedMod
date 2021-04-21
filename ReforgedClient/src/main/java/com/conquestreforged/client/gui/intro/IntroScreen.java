package com.conquestreforged.client.gui.intro;

import com.conquestreforged.client.tutorial.Tutorials;
import com.conquestreforged.core.config.section.ConfigSection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.CheckboxButton;
import net.minecraft.util.text.StringTextComponent;

public class IntroScreen extends Screen {

    private final Screen screen;
    private final ConfigSection section;
    private final CheckboxButton check = new CheckboxButton(0, 0, 0, 0, "Do not show again", false);

    public IntroScreen(Screen parent, ConfigSection section) {
        super(new StringTextComponent("Intro"));
        this.screen = parent;
        this.section = section;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public void onClose() {
        section.set("ignore_intro", check.isChecked());
        section.save();

        Minecraft.getInstance().displayGuiScreen(screen);
    }

    @Override
    public void init(Minecraft mc, int width, int height) {
        Tutorials.intro = true;
        super.init(mc, width, height);

        int center = width / 2;

        addButton(new Button(center - 50, height - 24, 100, 20, "Continue", b -> onClose()));

        check.setWidth(20);
        check.setHeight(20);
        check.y = height - 24;
        check.x = center + 50 + 8;
        addButton(check);
    }

    @Override
    public void render(int mx, int my, float ticks) {
        renderBackground();

        String[] message = new String[]{"Welcome to the Conquest Reforged 1.15.2 Alpha!",
                "This is an intro to keybindings to make your building process smoother.",
                "",
                "\"V\" - (Creative Mode only) opens block palette wheel for selecting shape variants of a certain texture",
                "This is to quickly find vertical slabs, stairs, etc.",
                "Make sure that you're hovering over a block with your cursor or that you have a block in hand.",
                "",
                "\"B\" - pressing this cycles through the block placement toggles 0-7.",
                "This is for placing different shape variant sizes (slabs, layers, arches, vert slabs, etc)."
        };

        int dist = 15;

        for(int i = 0; i < message.length; i++) {
            int titleWidth = font.getStringWidth(message[i]);
            int titleOffset = titleWidth / 2;
            font.drawStringWithShadow(message[i], width / 2F - titleOffset , 70 + i * dist, 0xFFFFFF);
        }

        //font.wrapFormattedStringToWidth(message, 200);
        //font.drawStringWithShadow(message, (width / 2F) - titleOffset,  200, 0xFFFFFF);

        super.render(mx, my, ticks);
    }

}
