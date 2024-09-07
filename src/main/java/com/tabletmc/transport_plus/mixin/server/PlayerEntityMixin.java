package com.tabletmc.transport_plus.mixin.server;

import com.tabletmc.transport_plus.impl.NetheriteArmorImpl;
import net.minecraft.block.BlockState;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    // Disable block breaking slowdown on elden horse
    @Inject(method="getBlockBreakingSpeed", at=@At("RETURN"), cancellable = true)
    public void getBlockBreakingSpeed(BlockState block, CallbackInfoReturnable<Float> cir) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        if (player.hasVehicle()) {
            if (player.getVehicle() instanceof AnimalEntity mount)
                if (((NetheriteArmorImpl) mount).hasNetheriteArmor())
                    cir.setReturnValue(cir.getReturnValue() * 5.0F);
        }
    }

}
