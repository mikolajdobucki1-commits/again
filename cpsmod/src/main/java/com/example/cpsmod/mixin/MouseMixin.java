package com.example.cpsmod.mixin;

import com.example.cpsmod.ClickTracker;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Hooks into GLFW's raw mouse-button callback so every press is counted,
 * even ones the game itself ignores (e.g. clicking while a GUI is open).
 */
@Mixin(Mouse.class)
public class MouseMixin {

    private static final int GLFW_PRESS = 1;

    @Inject(method = "onMouseButton", at = @At("HEAD"))
    private void cpsmod$onMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
        if (action == GLFW_PRESS) {
            ClickTracker.INSTANCE.registerClick(button);
        }
    }
}
