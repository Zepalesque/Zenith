package net.zepalesque.zenith.entity.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.zenith.api.blockset.BaseWoodSet;

import javax.annotation.Nonnull;

public class ZenithBoat extends Boat implements ZenithBoatBehavior {
    private BaseWoodSet set;
    public ZenithBoat(EntityType<? extends ZenithBoat> type, Level level) {
        super(type, level);
    }

    public ZenithBoat(BaseWoodSet set, Level level, double x, double y, double z) {
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
}
