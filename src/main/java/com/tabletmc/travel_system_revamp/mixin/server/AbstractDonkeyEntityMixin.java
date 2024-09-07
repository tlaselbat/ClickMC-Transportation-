package com.tabletmc.travel_system_revamp.mixin.server;

import com.tabletmc.travel_system_revamp.impl.NetheriteArmorImpl;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.tabletmc.travel_system_revamp.item.ModItems.NETHERITE_HORSE_ARMOR;

@Mixin(value = LivingEntity.class)
public abstract class AnimalEntityMixin implements NetheriteArmorImpl {

    @Unique private boolean mountArmor = false;
    public boolean hasNetheriteArmor() {
        return mountArmor;
    }

    public void updateNetheriteArmor() {
        // Get the mount's current body armor
        Item currentArmor = ((AnimalEntity) (Object) this).getBodyArmor().getItem();


        // Check if the current armor is Netherite Horse Armor
        mountArmor = currentArmor.equals(NETHERITE_HORSE_ARMOR.asItem());
    }

    @Inject(method="writeCustomDataToNbt", at = @At("HEAD"))
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("MountArmor", mountArmor);
    }

    @Inject(method="readCustomDataFromNbt", at = @At("HEAD"))
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        mountArmor = nbt.getBoolean("MountArmor");
    }



    /**
     * This method is injected into the 'tickControlled' method of the AbstractHorseEntity class.
     * It is called at the head of the method, meaning it will be executed before the original code in 'tickControlled'.
     */
//    @Inject(method = "tickControlled", at = @At("HEAD"))
//    private void tickControlled(CallbackInfo ci) {
//        // Cast the current object to an AbstractHorseEntity, which is the class being mixed into.
//        AbstractHorseEntity ahe = (AbstractHorseEntity)(Object)this;
//
//        // Check if the mount has a player rider and if it has Netherite armor.
//        // This is the condition under which the special jumping behavior will be triggered.
//        if (ahe.hasPlayerRider() && hasNetheriteArmor()) {
//            // If the mount is on the ground and not in the air, reset the doubleJumped flag.
//            // This is necessary to allow the mount to jump again after landing.
//            if (ahe.isOnGround()
//            ) doubleJumped = false;
//
//            // Check if the mount has not already double jumped, and if it has enough jump strength.
//            // Also check if the mount is in the air and not on the ground.
//            // If all these conditions are true, the mount will perform a special jump.
//            if (!doubleJumped && jumpStrength > 0.0F && ahe.isInAir() && !ahe.isOnGround())  {
//                // Set the mount's velocity to its current velocity, but with the y-component modified by the jump boost velocity modifier.
//                // This gives the mount an upward boost.
//                ahe.setVelocity(ahe.getVelocity().x, ahe.getAttributeValue(EntityAttributes.GENERIC_JUMP_STRENGTH) , ahe.getVelocity().z);
//
//                // Calculate the horizontal components of the mount's velocity based on its yaw (rotation around the y-axis).
//                // These components will be used to give the mount a horizontal boost.
//                float h = MathHelper.sin(ahe.getYaw() * 0.017453292F);
//                float i = MathHelper.cos(ahe.getYaw() * 0.017453292F);
//
//                // Add the horizontal boost to the mount's velocity.
//                // The boost is proportional to the jump strength and the horizontal components calculated above.
//                ahe.setVelocity(ahe.getVelocity().add(-0.4F * h * jumpStrength, 0.1F, 0.4F * i * jumpStrength));
//
//                // Set the doubleJumped flag to true, to prevent the mount from jumping again until it lands.
//                doubleJumped = true;
//            }
//        }
//    }

    }

