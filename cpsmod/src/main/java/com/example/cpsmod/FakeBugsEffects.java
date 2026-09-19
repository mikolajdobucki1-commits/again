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

/**
 * All "fake bug" prank effects live here. Every single one is 100%
 * client-side and cosmetic: nothing is sent to the server, no real item,
 * mob, or block state is created or changed, and nobody else on a
 * multiplayer server ever sees any of it (only entities the server itself
 * spawns and syncs are visible to other players). Everything here vanishes
 * the moment the client re-syncs with the server.
 */
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
        // addEntity is the method vanilla itself uses to attach a local,
        // client-only entity to the render/tracking system (spawnEntity
        // alone isn't reliable for entities that didn't come from the
        // server).
        client.world.addEntity(ghostItem.getId(), ghostItem);

        feedback(client, "§7[fakebugs] spawned a client-only ghost item (visual only)");
    }

    public static void runGhostMob(MinecraftClient client) {
        if (client.player == null || client.world == null) return;

        ZombieEntity ghost = new ZombieEntity(EntityType.ZOMBIE, client.world);
        Vec3d pos = client.player.getPos().add(2, 0, 0);
        ghost.setPosition(pos.x, pos.y, pos.z);
        client.world.addEntity(ghost.getId(), ghost);

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
                SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, 1.0F);

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
