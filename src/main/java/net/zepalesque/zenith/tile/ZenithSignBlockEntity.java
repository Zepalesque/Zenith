package net.zepalesque.zenith.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ZenithSignBlockEntity extends SignBlockEntity {
    private final BlockEntityType<ZenithSignBlockEntity> signBlockEntity;
    public ZenithSignBlockEntity(BlockPos pos, BlockState state, BlockEntityType<ZenithSignBlockEntity> pSignBlockEntity) {
        super(pos, state);
        this.signBlockEntity = pSignBlockEntity;
    }

    public BlockEntityType<ZenithSignBlockEntity> getType() {
        return this.signBlockEntity;
    }
}
