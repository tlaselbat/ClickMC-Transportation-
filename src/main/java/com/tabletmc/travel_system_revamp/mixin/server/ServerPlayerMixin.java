package com.tabletmc.travel_system_revamp.mixin.server;

import com.tabletmc.travel_system_revamp.impl.AbstractHorseEntityImpl;
import com.tabletmc.travel_system_revamp.impl.EntityMixinImpl;
import com.tabletmc.travel_system_revamp.impl.ServerPlayerEntityImpl;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerMixin implements ServerPlayerEntityImpl {
    @Shadow public abstract void sendMessage(Text message, boolean actionBar);
    @Unique ServerPlayerMixin playerEntity = this;
    @Unique HorseEntity storedHorse = playerEntity.getHorse();     /**
     * Summons a horse entity for the player, optionally mounting the player on the horse.
     *
     * @param mountPlayer Whether to mount the player on the horse
     */
    public void summonHorse(boolean mountPlayer) {
        if (storedHorse == null) {
            sendMessage(Text.of("No Horse Found!"), true);
            return;
        }

        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        storedHorse.fallDistance = player.fallDistance;
        storedHorse.refreshPositionAndAngles(player.getX(), player.getY(), player.getZ(), player.getYaw(), player.getPitch());

        if (mountPlayer) {
            player.startRiding(storedHorse, true);
        }

        storedHorse.setVelocity(player.getVelocity());
        player.getWorld().spawnEntity(storedHorse);

        if (!mountPlayer) {
            storedHorse.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 60, 0, false, false));
        }
    }

    public void dismountHorse(boolean mountPlayer) {
        if (storedHorse == null) {
            return; // No horse to dismount
        }
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;


        // Remove the horse from the player's riding entity
        if (player.getRootVehicle() != null) {
            player.stopRiding();
            player.getRootVehicle().dismountVehicle();
        }

    }

    /**
     * Stores a horse entity in the player's data.
     *
     * @param horse The horse entity to store.
     */
    @Override
    public void storeHorse(HorseEntity horse) {
        if (storedHorse != null && !storedHorse.getUuid().equals(horse.getUuid())) {
            sendMessage(Text.of("[travel_system_revamp]: Replaced Old Horse"), false);
            summonHorse(false);
        }

        if (horse.getRemovalReason() != null) {
            storedHorse = null;
        } else {
            storedHorse = horse;
        }
    }

    /**
     * Reads custom data from NBT and deserializes the stored horse entity.
     *
     * @param nbt The NBT compound containing the player's custom data.
     * @param ci  The callback info for the method injection.
     */
    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        NbtCompound horseNbt = nbt.getCompound("travel_system_revamp_Horse");
        if (!horseNbt.isEmpty()) {
            storedHorse = (HorseEntity) EntityType.getEntityFromNbt(horseNbt, ((ServerPlayerEntity) (Object) this).getWorld()).get();
        }
    }

    /**
     * Injects custom data into the NBT compound when writing player data.
     *
     * @param nbt The NBT compound to write data to.
     * @param ci  The callback info for the method injection.
     */
    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        if (storedHorse != null && !((ServerPlayerEntity) (Object) this).hasVehicle()) {
            NbtCompound tag = new NbtCompound();
            storedHorse.saveSelfNbt(tag);
            nbt.put("travel_system_revamp_Horse", tag);
        }
    }

    /**
     * Injects a method that is called when the player starts riding an entity.
     *
     * @param entity The entity that the player is riding.
     * @param force  Whether the player is forced to start riding.
     * @param cir    The callback info for the method injection.
     */
    @Inject(method = "startRiding", at = @At("TAIL"))
    public void startRiding(Entity entity, boolean force, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof HorseEntity horse && ((AbstractHorseEntityImpl) horse).hasNetheriteArmor()) {
            storeHorse(horse);
        }
    }

    /**
     * Injects a method that is called when the player stops riding an entity.
     *
     * @param ci The callback info for the method injection.
     */
    @Inject(method = "stopRiding", at = @At("TAIL"))
    public void stopRiding(CallbackInfo ci) {
            if (storedHorse == null) return;

            if (!((AbstractHorseEntityImpl) storedHorse).hasNetheriteArmor() || storedHorse.getRemovalReason() != null) {
                storedHorse = null;
                return;
            }
            storedHorse.remove(Entity.RemovalReason.DISCARDED);
            ((EntityMixinImpl) storedHorse).undoRemove();  // Set removalReason to null so that horse can be spawned
            ((ServerPlayerEntity)(Object)this).fallDistance = storedHorse.fallDistance;
        }
  }



