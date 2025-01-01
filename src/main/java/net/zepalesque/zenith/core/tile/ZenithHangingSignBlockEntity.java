package net.zepalesque.zenith.core.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.zenith.api.blockset.type.AbstractWoodSet;

import java.util.function.Supplier;

public class ZenithHangingSignBlockEntity extends HangingSignBlockEntity {
    protected final AbstractWoodSet woodset;

    protected <T extends AbstractWoodSet> ZenithHangingSignBlockEntity(BlockPos pos, BlockState state, T woodset) {
        super(pos, state);
        this.woodset = woodset;
    }

    // Fix to get around crashing caused by the validateBlockState method
    public static <T extends AbstractWoodSet> ZenithHangingSignBlockEntity create(BlockPos pos, BlockState state, T set) {
        return new ZenithHangingSignBlockEntity(pos, state, set) {
            @Override
            public BlockEntityType<? extends ZenithHangingSignBlockEntity> getType() {
                return this.woodset == null ? set.hangingSignEntity().get() : this.woodset.hangingSignEntity().get();
            }
        };
    }

    @Override
    public BlockEntityType<? extends ZenithHangingSignBlockEntity> getType() {
        return this.woodset.hangingSignEntity().get();
    }
}
