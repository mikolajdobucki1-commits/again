package com.example.cpsmod;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class FakeBugsScreen extends Screen {

    public FakeBugsScreen() {
        super(Text.literal("Fake Bugs"));
    }

    @Override
    protected void init() {
        int startY = this.height / 2 - 80;
        int spacing = 24;

        addOption("Fake Dupe", startY, () -> FakeBugsEffects.runDupe(client));
        addOption("Ghost Mob", startY + spacing, () -> FakeBugsEffects.runGhostMob(client));
        addOption("Screen Glitch", startY + spacing * 2, () -> FakeBugsEffects.runGlitch(client));
        addOption("Fake Explosion", startY + spacing * 3, () -> FakeBugsEffects.runExplosion(client));
        addOption("Fake Achievement", startY + spacing * 4, () -> FakeBugsEffects.runFakeToast(client));

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Close"), btn -> close())
                .dimensions(this.width / 2 - 100, startY + spacing * 5 + 10, 200, 20)
                .build());
    }

    private void addOption(String label, int y, Runnable action) {
        this.addDrawableChild(ButtonWidget.builder(Text.literal(label), btn -> {
            action.run();
            close();
        }).dimensions(this.width / 2 - 100, y, 200, 20).build());
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
