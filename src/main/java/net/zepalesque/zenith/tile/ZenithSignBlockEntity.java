package net.zepalesque.zenith.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class ZenithSignBlockEntity extends SignBlockEntity {
    private final Supplier<BlockEntityType<? extends ZenithSignBlockEntity>> type;
    public ZenithSignBlockEntity(BlockPos pos, BlockState state, Supplier<BlockEntityType<? extends ZenithSignBlockEntity>> type) {
        super(pos, state);
        this.type = type;
    }

    public BlockEntityType<? extends ZenithSignBlockEntity> getType() {
        return this.type.get();
    }
}
