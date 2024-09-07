package com.tabletmc.transport_plus.mixin.client;

import com.tabletmc.transport_plus.impl.NetheriteArmorImpl;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerMixin {
    @Inject(method = "startRiding", at = @At("HEAD"))
    public void startRiding(Entity entity, boolean force, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof AnimalEntity horse) {((NetheriteArmorImpl) horse).updateNetheriteArmor();}
    }
}
