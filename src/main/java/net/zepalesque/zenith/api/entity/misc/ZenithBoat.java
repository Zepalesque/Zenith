package net.zepalesque.zenith.api.entity.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.zenith.api.blockset.type.base.AbstractWoodSet;
import net.zepalesque.zenith.core.Zenith;

import javax.annotation.Nonnull;

public class ZenithBoat extends Boat implements ZenithBoatBehavior {
    protected AbstractWoodSet set;
    public ZenithBoat(EntityType<? extends ZenithBoat> type, Level level) {
        super(type, level);
    }

    public ZenithBoat(AbstractWoodSet set, Level level, double x, double y, double z) {
        this(set.boatEntity().get(), level);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.set = set;
    }

    @Nonnull
    public Item getDropItem() {
        return getBoat();
    }

    protected void checkFallDamage(double y, boolean onGround, @Nonnull BlockState state, @Nonnull BlockPos pos) {
        this.fall(this, y, onGround);
    }

    @Override
    public Item getPlanks() {
        return this.set == null ? Items.AIR : this.set.planks().get().asItem();

    }

    @Override
    public Item getStick() {
        return this.set == null ? Items.STICK : this.set.getStick().get();
    }

    @Override
    public Item getBoat() {
        return this.set == null ? Items.AIR : this.set.boatItem().get();
    }

    public ZenithBoat withSet(AbstractWoodSet set) {
        if (this.set == null) {
            this.set = set;
        } else {
            Zenith.LOGGER.warn("Tried to set AbstractWoodSet of ZenithBoat to {}, when it already was a part of set {}! Ignoring...", set.getID(), this.set.getID());
        }
        return this;
    }
}
