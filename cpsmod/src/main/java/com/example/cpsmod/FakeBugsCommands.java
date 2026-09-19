package com.example.cpsmod;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class FakeBugsCommands {

    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(
                        literal("fakebugs")
                                .then(literal("dupe").executes(ctx -> {
                                    FakeBugsEffects.runDupe(MinecraftClient.getInstance());
                                    return 1;
                                }))
                                .then(literal("ghostmob").executes(ctx -> {
                                    FakeBugsEffects.runGhostMob(MinecraftClient.getInstance());
                                    return 1;
                                }))
                                .then(literal("glitch").executes(ctx -> {
                                    FakeBugsEffects.runGlitch(MinecraftClient.getInstance());
                                    return 1;
                                }))
                                .then(literal("explosion").executes(ctx -> {
                                    FakeBugsEffects.runExplosion(MinecraftClient.getInstance());
                                    return 1;
                                }))
                                .then(literal("toast").executes(ctx -> {
                                    FakeBugsEffects.runFakeToast(MinecraftClient.getInstance());
                                    return 1;
                                }))
                                .then(literal("help").executes(ctx -> {
                                    ctx.getSource().sendFeedback(Text.literal(
                                            "§e[fakebugs] dupe, ghostmob, glitch, explosion, toast \u2014 or just press N in-game"));
                                    return 1;
                                }))
                )
        );
    }
}
