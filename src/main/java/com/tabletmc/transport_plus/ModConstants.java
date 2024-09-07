package com.tabletmc.transport_plus;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModConstants {

    public static final String MOD_ID = "transport_plus";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static Identifier Id(String path) {
        return Identifier.of(MOD_ID, path);
    }
     public static boolean summonCooldown = false;
    public static void summonCooldown() {

        // Start a new thread to handle the cooldown period.
        new Thread(() -> {
            summonCooldown = true;
            LOGGER.info("Cooldown started.");
            try {
                // Wait for one second.
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            } finally {
                summonCooldown = false;
                LOGGER.info("Cooldown ended.");}})
                .start();
    }
}