package com.example.cpsmod;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;

public class CpsHud {

    private static final int X = 6;
    private static final int Y = 6;
    private static final int COLOR = 0xFFFFFF;

    public static void register() {
        HudRenderCallback.EVENT.register(CpsHud::render);
    }

    private static void render(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options.hudHidden) {
            return;
        }

        int left = ClickTracker.INSTANCE.getLeftCps();
        int right = ClickTracker.INSTANCE.getRightCps();

        String text = "CPS: " + left + " (LMB)  " + right + " (RMB)";
        context.drawTextWithShadow(client.textRenderer, Text.literal(text), X, Y, COLOR);
    }
}
