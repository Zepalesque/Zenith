package net.zepalesque.zenith.core.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class ZenithHangingSignBlockEntity extends HangingSignBlockEntity {
    protected final Supplier<BlockEntityType<? extends ZenithHangingSignBlockEntity>> typeSupplier;

    protected ZenithHangingSignBlockEntity(BlockPos pos, BlockState state, Supplier<BlockEntityType<? extends ZenithHangingSignBlockEntity>> typeSupplier) {
        super(pos, state);
        this.typeSupplier = typeSupplier;
    }

    // Fix to get around crashing caused by the validateBlockState method
    public static ZenithHangingSignBlockEntity create(BlockPos pos, BlockState state, Supplier<BlockEntityType<? extends ZenithHangingSignBlockEntity>> set) {
        return new ZenithHangingSignBlockEntity(pos, state, set) {
            @Override
            public BlockEntityType<? extends ZenithHangingSignBlockEntity> getType() {
                return this.typeSupplier == null ? set.get() : this.typeSupplier.get();
            }
        };
    }

    @Override
    public BlockEntityType<? extends ZenithHangingSignBlockEntity> getType() {
        return this.typeSupplier.get();
    }
}
