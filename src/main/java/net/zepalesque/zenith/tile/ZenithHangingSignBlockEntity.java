package net.zepalesque.zenith.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ZenithHangingSignBlockEntity extends HangingSignBlockEntity {
    private final BlockEntityType<ZenithHangingSignBlockEntity> beType;
    public ZenithHangingSignBlockEntity(BlockPos pos, BlockState state, BlockEntityType<ZenithHangingSignBlockEntity> beType) {
        super(pos, state);
        this.beType = beType;
    }

    public BlockEntityType<ZenithHangingSignBlockEntity> getType() {
        return this.beType;
    }
}
