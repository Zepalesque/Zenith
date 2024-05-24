package net.zepalesque.zenith.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public abstract class ZenithWallHangingSignBlock extends WallHangingSignBlock {
    public ZenithWallHangingSignBlock(WoodType woodType, Properties properties) {
        super(woodType, properties);
    }

    @Override
    public abstract BlockEntity newBlockEntity(BlockPos pos, BlockState state);
}
