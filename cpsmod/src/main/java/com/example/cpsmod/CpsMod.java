package com.example.cpsmod;

import net.fabricmc.api.ClientModInitializer;

public class CpsMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CpsHud.register();
        KeystrokesHud.register();
        FakeBugsCommands.register();
        FakeBugsKeybind.register();
    }
}
