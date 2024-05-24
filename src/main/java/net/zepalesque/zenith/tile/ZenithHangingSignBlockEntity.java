package net.zepalesque.zenith.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class ZenithHangingSignBlockEntity extends HangingSignBlockEntity {
    private final Supplier<BlockEntityType<? extends ZenithHangingSignBlockEntity>> type;
    public ZenithHangingSignBlockEntity(BlockPos pos, BlockState state, Supplier<BlockEntityType<? extends ZenithHangingSignBlockEntity>> type) {
        super(pos, state);
        this.type = type;
    }

    public BlockEntityType<? extends ZenithHangingSignBlockEntity> getType() {
        return this.type.get();
    }
}
