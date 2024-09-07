package com.tabletmc.travel_system_revamp.mixin.server;

import com.tabletmc.travel_system_revamp.impl.AbstractHorseEntityImpl;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.tabletmc.travel_system_revamp.item.ModItems.NETHERITE_HORSE_ARMOR;

@Mixin(value = AbstractHorseEntity.class)
public abstract class AbstractHorseEntityMixin implements AbstractHorseEntityImpl {
    @Unique
    private boolean doubleJumped = false;
    @Shadow protected float jumpStrength;
    @Unique private boolean mountArmor = false;
    public boolean hasNetheriteArmor() {
        return mountArmor;
    }

    /**
     * Updates the tsrArmor flag based on the horse's current armor.
     *
     * This method checks if the horse is wearing Netherite Horse Armor and sets the tsrArmor flag accordingly.
     */
    public void updateNetheriteArmor() {
        // Get the horse's current body armor
        Item currentArmor = ((HorseEntity) (Object) this).getBodyArmor().getItem();


        // Check if the current armor is Netherite Horse Armor
        mountArmor = currentArmor.equals(NETHERITE_HORSE_ARMOR.asItem());
    }

    @Inject(method="writeCustomDataToNbt", at = @At("HEAD"))
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("HorseArmor", mountArmor);
    }

    @Inject(method="readCustomDataFromNbt", at = @At("HEAD"))
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        mountArmor = nbt.getBoolean("HorseArmor");
    }



    /**
     * This method is injected into the 'tickControlled' method of the AbstractHorseEntity class.
     * It is called at the head of the method, meaning it will be executed before the original code in 'tickControlled'.
     */
    @Inject(method = "tickControlled", at = @At("HEAD"))
    private void tickControlled(CallbackInfo ci) {
        // Cast the current object to an AbstractHorseEntity, which is the class being mixed into.
        AbstractHorseEntity ahe = (AbstractHorseEntity)(Object)this;

        // Check if the horse has a player rider and if it has Netherite armor.
        // This is the condition under which the special jumping behavior will be triggered.
        if (ahe.hasPlayerRider() && hasNetheriteArmor()) {
            // If the horse is on the ground and not in the air, reset the doubleJumped flag.
            // This is necessary to allow the horse to jump again after landing.
            if (ahe.isOnGround()
            ) doubleJumped = false;

            // Check if the horse has not already double jumped, and if it has enough jump strength.
            // Also check if the horse is in the air and not on the ground.
            // If all these conditions are true, the horse will perform a special jump.
            if (!doubleJumped && jumpStrength > 0.0F && ahe.isInAir() && !ahe.isOnGround())  {
                // Set the horse's velocity to its current velocity, but with the y-component modified by the jump boost velocity modifier.
                // This gives the horse an upward boost.
                ahe.setVelocity(ahe.getVelocity().x, ahe.getAttributeValue(EntityAttributes.GENERIC_JUMP_STRENGTH) , ahe.getVelocity().z);

                // Calculate the horizontal components of the horse's velocity based on its yaw (rotation around the y-axis).
                // These components will be used to give the horse a horizontal boost.
                float h = MathHelper.sin(ahe.getYaw() * 0.017453292F);
                float i = MathHelper.cos(ahe.getYaw() * 0.017453292F);

                // Add the horizontal boost to the horse's velocity.
                // The boost is proportional to the jump strength and the horizontal components calculated above.
                ahe.setVelocity(ahe.getVelocity().add(-0.4F * h * jumpStrength, 0.1F, 0.4F * i * jumpStrength));

                // Set the doubleJumped flag to true, to prevent the horse from jumping again until it lands.
                doubleJumped = true;
            }
        }
    }

    }

