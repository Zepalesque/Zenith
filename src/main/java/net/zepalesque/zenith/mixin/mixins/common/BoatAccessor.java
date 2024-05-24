package net.zepalesque.zenith.mixin.mixins.common;

import net.minecraft.world.entity.vehicle.Boat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Boat.class)
public interface BoatAccessor {
    @Accessor("lastYd")
    void zenith$setLastYd(double lastYd);

    @Accessor("status")
    Boat.Status zenith$getStatus();
}