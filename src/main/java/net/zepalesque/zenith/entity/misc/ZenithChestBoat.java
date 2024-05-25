package net.zepalesque.zenith.entity.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.zenith.api.blockset.AbstractWoodSet;

import javax.annotation.Nonnull;

public class ZenithChestBoat extends ChestBoat implements ZenithBoatBehavior {
    private AbstractWoodSet set;
    public ZenithChestBoat(EntityType<? extends ZenithChestBoat> type, Level level) {
        super(type, level);
    }

    public ZenithChestBoat(AbstractWoodSet set, Level level, double x, double y, double z) {
        this(set.chestBoatEntity().get(), level);
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
        return this.set == null ? Items.AIR : this.set.chestBoatItem().get();
    }
}
