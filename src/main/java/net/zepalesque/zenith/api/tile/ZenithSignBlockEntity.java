package net.zepalesque.zenith.api.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class ZenithSignBlockEntity extends SignBlockEntity {

    public ZenithSignBlockEntity(BlockPos pos, BlockState state, Supplier<BlockEntityType<? extends ZenithSignBlockEntity>> type) {
        super(type.get(), pos, state);
    }
}
