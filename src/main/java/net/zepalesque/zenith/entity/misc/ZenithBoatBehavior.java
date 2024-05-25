package net.zepalesque.zenith.entity.misc;

import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.GameRules;
import net.zepalesque.zenith.mixin.mixins.common.accessor.BoatAccessor;

public interface ZenithBoatBehavior {
    default void fall(Boat boat, double y, boolean onGround) {
        BoatAccessor boatAccessor = (BoatAccessor) boat;
        boatAccessor.zenith$setLastYd(boat.getDeltaMovement().y);
        if (!boat.isPassenger()) {
            if (onGround) {
                if (boat.fallDistance > 3.0F) {
                    if (boatAccessor.zenith$getStatus() != Boat.Status.ON_LAND) {
                        boat.resetFallDistance();
                        return;
                    }
                    boat.causeFallDamage(boat.fallDistance, 1.0F, boat.damageSources().fall());
                    if (!boat.level().isClientSide && !boat.isRemoved()) {
                        boat.kill();
                        if (boat.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                            for(int i = 0; i < 3; ++i) {
                                boat.spawnAtLocation(getPlanks());
                            }

                            for(int j = 0; j < 2; ++j) {
                                boat.spawnAtLocation(getStick());
                            }
                        }
                    }
                }
                boat.resetFallDistance();
            } else if (!boat.level().getFluidState(boat.blockPosition().below()).is(FluidTags.WATER) && y < 0.0D) {
                boat.fallDistance -= (float) y;
            }
        }
    }

    Item getStick();
    Item getPlanks();
    Item getBoat();

}
