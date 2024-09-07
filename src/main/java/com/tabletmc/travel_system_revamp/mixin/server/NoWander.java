package com.tabletmc.travel_system_revamp.mixin.server;

import com.tabletmc.travel_system_revamp.config.ModConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// Lower wander speed for saddled horses
@Mixin(value = LivingEntity.class, priority = 960)
@SuppressWarnings("unused")
public abstract class NoWander {
    /**
     * Modifies the movement speed of a mount to prevent wandering when it is saddled and the noWander config option is enabled.
     *
     * @param input the original movement vector
     * @return the modified movement vector, or Vec3d.ZERO if wandering should be prevented
     */
    @ModifyArg(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;travel(Lnet/minecraft/util/math/Vec3d;)V"))
    private Vec3d lowerWanderSpeed(Vec3d input) {
        // Check if the noWander config option is enabled and the mount is saddled
        if (ModConfig.getConfig().noWander
                && (mount() instanceof AbstractHorseEntity horse
                && horse.isSaddled())) {
            // If so, return a zero movement vector to prevent wandering
            return Vec3d.ZERO;
        }
        // Otherwise, return the original movement vector
        return input;
    }

    /**
     * A helper method that casts the current object to a LivingEntity.
     *
     * @return the current object as a LivingEntity
     */
    @Unique
    private LivingEntity mount() {
        return ((LivingEntity)(Object)this);
    }
}
