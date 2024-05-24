package net.zepalesque.zenith.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public abstract class ZenithCeilingHangingSignBlock extends CeilingHangingSignBlock {
    public ZenithCeilingHangingSignBlock(WoodType type, Properties properties) {
        super(type, properties);
    }

    @Override
    public abstract BlockEntity newBlockEntity(BlockPos pos, BlockState state);
}
