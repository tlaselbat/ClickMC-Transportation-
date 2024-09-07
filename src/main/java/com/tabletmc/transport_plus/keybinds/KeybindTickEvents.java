package com.tabletmc.transport_plus.keybinds;

import com.tabletmc.transport_plus.net.payload.StringPayload;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

import static com.tabletmc.transport_plus.ModConstants.summonCooldown;
import static com.tabletmc.transport_plus.keybinds.RegisterKeybinds.SUMMON_HORSE;

/**
 * Registers client-side tick events for the mod.
 * This class handles the logic for summoning and dismounting horses.
 */
public class KeybindTickEvents {

    /**
     * Flag to track whether the summon horse action is on cooldown.
     * This is used to prevent spamming the summon horse keybind.
     */
    static MinecraftClient client = MinecraftClient.getInstance();
    static ClientPlayerEntity player = client.player;

    /**
     * Initializes the client-side tick events.
     * This method registers the onEndTick method to be called at the end of each client tick.
     */
    public static void init() {
        // Register the onEndTick method to be called at the end of each client tick.
        ClientTickEvents.END_CLIENT_TICK.register(KeybindTickEvents::onEndTick);
    }

    /**
     * Called at the end of each client tick.
     * This method checks if the summon horse keybind is pressed and handles the logic for summoning and dismounting horses.
     *
     * @param client the Minecraft client instance.
     */
    private static void onEndTick(MinecraftClient client) {
        if (client == null) {
            // Handle null client
            return;
        }

        player = client.player;
        // Check if the summon horse keybind is pressed and the summon action is not on cooldown.
        if (SUMMON_HORSE.isPressed() && !summonCooldown) {
            // Check if the player has a vehicle.
            if (client.player != null && client.player.hasVehicle()) {
                try {
                    // Send a dismount packet to the server.
                    ClientPlayNetworking.send(new StringPayload("dismount"));
                } catch (IllegalStateException ignored) {
                }
            }
            // Call the summon cooldown method to prevent spamming the summon horse keybind.
            summonCooldown();
            // Check if the summon horse keybind is still pressed and the summon action is not on cooldown.
            if (SUMMON_HORSE.isPressed() && !summonCooldown) {
                // Check if the player does not have a vehicle.
                if (client.player != null && !client.player.hasVehicle()) {
                    try {
                        // Send a summon packet to the server.
                        ClientPlayNetworking.send(new StringPayload("summon"));
                    } catch (IllegalStateException ignored) {
                        // If the mod is not on the server, send a message to the player.
                        client.player.sendMessage(Text.of("Failed: Mod Not On Server?"), true);
                    }
                    // Call the summon cooldown method to prevent spamming the summon horse keybind.
                    summonCooldown();
                }
            }
        }
    }
}