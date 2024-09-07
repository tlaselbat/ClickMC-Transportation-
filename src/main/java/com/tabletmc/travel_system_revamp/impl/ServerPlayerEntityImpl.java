package com.tabletmc.travel_system_revamp.impl;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public interface ServerPlayerEntityImpl {

    void storeHorse(HorseEntity horse);

    void summonHorse(boolean mountPlayer);

    void dismountHorse(boolean mountPlayer);

    default HorseEntity getHorse() {
        Entity storedHorse = ((ServerPlayerEntity) this).getVehicle();
        return (HorseEntity) storedHorse;
    }





}
