package com.tabletmc.travel_system_revamp.mixin.server;

import com.tabletmc.travel_system_revamp.impl.EntityMixinImpl;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityMixinImpl {
    @Shadow @Nullable private Entity.RemovalReason removalReason;

    public void undoRemove() { removalReason = null; }
}
