package com.example.cpsmod;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;

public class KeystrokesHud {

    private static final int KEY_SIZE = 20;
    private static final int GAP = 3;
    private static final int MARGIN = 10;

    private static final int COLOR_UP = 0x80202020;
    private static final int COLOR_DOWN = 0xE0FFFFFF;
    private static final int TEXT_UP = 0xFFFFFF;
    private static final int TEXT_DOWN = 0x000000;

    public static void register() {
        HudRenderCallback.EVENT.register(KeystrokesHud::render);
    }

    private static void render(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options.hudHidden || client.player == null) return;

        int blockWidth = KEY_SIZE * 3 + GAP * 2;
        int originX = context.getScaledWindowWidth() - blockWidth - MARGIN;
        int originY = context.getScaledWindowHeight() - (KEY_SIZE * 4 + GAP * 3) - MARGIN;

        drawKey(context, client, originX + KEY_SIZE + GAP, originY, "W", client.options.forwardKey);

        int row2Y = originY + KEY_SIZE + GAP;
        drawKey(context, client, originX, row2Y, "A", client.options.leftKey);
        drawKey(context, client, originX + KEY_SIZE + GAP, row2Y, "S", client.options.backKey);
        drawKey(context, client, originX + (KEY_SIZE + GAP) * 2, row2Y, "D", client.options.rightKey);

        int row3Y = row2Y + KEY_SIZE + GAP;
        drawKey(context, client, originX, row3Y, "LM", client.options.attackKey);
        drawKey(context, client, originX + (KEY_SIZE + GAP) * 2, row3Y, "RM", client.options.useKey);

        int row4Y = row3Y + KEY_SIZE + GAP;
        drawWideKey(context, client, originX, row4Y, blockWidth, "SPACE", client.options.jumpKey);
    }

    private static void drawKey(DrawContext context, MinecraftClient client, int x, int y, String label, KeyBinding binding) {
        drawBox(context, client, x, y, KEY_SIZE, label, binding.isPressed());
    }

    private static void drawWideKey(DrawContext context, MinecraftClient client, int x, int y, int width, String label, KeyBinding binding) {
        drawBox(context, client, x, y, width, label, binding.isPressed());
    }

    private static void drawBox(DrawContext context, MinecraftClient client, int x, int y, int width, String label, boolean pressed) {
        int bg = pressed ? COLOR_DOWN : COLOR_UP;
        int textColor = pressed ? TEXT_DOWN : TEXT_UP;

        context.fill(x, y, x + width, y + KEY_SIZE, bg);

        int textWidth = client.textRenderer.getWidth(label);
        int textX = x + (width - textWidth) / 2;
        int textY = y + (KEY_SIZE - client.textRenderer.fontHeight) / 2;
        context.drawTextWithShadow(client.textRenderer, Text.literal(label), textX, textY, textColor);
    }
}
