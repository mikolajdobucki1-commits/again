package com.example.cpsmod;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class FakeBugsKeybind {

    private static KeyBinding openMenuKey;

    public static void register() {
        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.cpsmod.fakebugs_menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_N,
                "category.cpsmod.fakebugs"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenuKey.wasPressed()) {
                if (client.player != null && client.currentScreen == null) {
                    client.setScreen(new FakeBugsScreen());
                }
            }
        });
    }
}
