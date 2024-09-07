package com.tabletmc.transport_plus.mixin.server;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.tabletmc.transport_plus.config.ModConfig;
import net.minecraft.entity.passive.AbstractHorseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

 
@Mixin(value = AbstractHorseEntity.class, priority = 960)
public class NoBuck {
    @ModifyReturnValue(method = "isAngry", at = @At("RETURN"))
    private boolean isAngry(boolean original) {
        AbstractHorseEntity instance = mount();
        if (ModConfig.getConfig().noBuck
                && !instance.jumping
                && instance.isTame()
                && instance.getControllingPassenger() != null) {
            return false;
        }
        return original;
    }

    @Unique
    private AbstractHorseEntity mount() {
        return ((AbstractHorseEntity)(Object)this);
    }
}
