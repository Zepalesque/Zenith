package net.zepalesque.zenith.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.function.Supplier;

public class ZenithCeilingHangingSignBlock extends CeilingHangingSignBlock {

    private final Supplier<BlockEntityType<?>> entity;

    public ZenithCeilingHangingSignBlock(WoodType woodType, Supplier<BlockEntityType<?>> entity, Properties properties) {
        super(woodType, properties);
        this.entity = entity;
    }

    @Override
    public  BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return this.entity.get().create(pos, state);
    }
}
