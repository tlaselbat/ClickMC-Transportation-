package com.tabletmc.transport_plus.mixin.server;

import com.tabletmc.transport_plus.impl.NetheriteArmorImpl;
import com.tabletmc.transport_plus.impl.ServerPlayerEntityImpl;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.HorseScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseScreenHandler.class)
public abstract class HorseScreenHandlerMixin {
    @Shadow @Final private AbstractHorseEntity entity;
    /**
     * Injected method that is called when the horse screen handler is closed.
     * This method is responsible for transferring the horse's netherite armor to the player's inventory.
     *
     * @param player the player who closed the horse screen handler
     * @param ci     the callback info
     */
    @Inject(method = "onClosed", at = @At("HEAD"))
    public void transferSlot(PlayerEntity player, CallbackInfo ci) {
        // Check if the entity is a horse
        if (entity instanceof HorseEntity horse) {
            // Update the horse's netherite armor
            ((NetheriteArmorImpl) horse).updateNetheriteArmor();

            // Check if the player is a server player
            if (player instanceof ServerPlayerEntity serverPlayer) {
                // Check if the horse is the player's vehicle and has netherite armor
                if (horse.equals(serverPlayer.getVehicle()) && ((NetheriteArmorImpl) horse).hasNetheriteArmor()) {
                    // Store the horse's data on the server player
                    ((ServerPlayerEntityImpl) serverPlayer).storeMount(horse);
                }
            }
        }
    }
}
