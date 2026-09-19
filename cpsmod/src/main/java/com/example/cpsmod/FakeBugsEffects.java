package com.example.cpsmod;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class FakeBugsEffects {

    private static final Random RNG = Random.create();

    public static void runDupe(MinecraftClient client) {
        if (client.player == null || client.world == null) return;

        ItemStack heldStack = client.player.getMainHandStack();
        if (heldStack.isEmpty()) {
            feedback(client, "§c[fakebugs] Hold an item first.");
            return;
        }

        ItemStack ghostStack = heldStack.copy();
        Vec3d pos = client.player.getPos().add(0, 0.3, 0);

        ItemEntity ghostItem = new ItemEntity(client.world, pos.x, pos.y, pos.z, ghostStack);
        ghostItem.setVelocity(
                (RNG.nextDouble() - 0.5) * 0.25,
                0.25,
                (RNG.nextDouble() - 0.5) * 0.25
        );
        client.world.addEntity(ghostItem);

        feedback(client, "§7[fakebugs] spawned a client-only ghost item (visual only)");
    }

    public static void runGhostMob(MinecraftClient client) {
        if (client.player == null || client.world == null) return;

        ZombieEntity ghost = new ZombieEntity(EntityType.ZOMBIE, client.world);
        Vec3d pos = client.player.getPos().add(2, 0, 0);
        ghost.setPosition(pos.x, pos.y, pos.z);
        client.world.addEntity(ghost);

        feedback(client, "§7[fakebugs] spawned a client-only mob (visual only)");
    }

    public static void runGlitch(MinecraftClient client) {
        if (client.player == null) return;
        client.player.sendMessage(Text.literal("§k||||§r §c[fakebugs] glitch §k||||"), true);
        feedback(client, "§7[fakebugs] triggered a fake glitch flash");
    }

    public static void runExplosion(MinecraftClient client) {
        if (client.player == null || client.world == null) return;

        Vec3d pos = client.player.getPos().add(client.player.getRotationVector().multiply(3));
        client.world.addParticle(ParticleTypes.EXPLOSION_EMITTER, pos.x, pos.y, pos.z, 0, 0, 0);
        client.world.playSound(client.player, client.player.getBlockPos(),
                SoundEvents.ENTITY_GENERIC_EXPLODE.value(), SoundCategory.BLOCKS, 4.0F, 1.0F);

        feedback(client, "§7[fakebugs] triggered a fake explosion (visual + sound only, no real damage)");
    }

    public static void runFakeToast(MinecraftClient client) {
        client.getToastManager().add(SystemToast.create(
                client,
                SystemToast.Type.PERIODIC_NOTIFICATION,
                Text.literal("Achievement Get!"),
                Text.literal("You broke the game (not really)")
        ));
        feedback(client, "§7[fakebugs] triggered a fake achievement popup");
    }

    private static void feedback(MinecraftClient client, String message) {
        if (client.player != null) {
            client.player.sendMessage(Text.literal(message), false);
        }
    }
}
