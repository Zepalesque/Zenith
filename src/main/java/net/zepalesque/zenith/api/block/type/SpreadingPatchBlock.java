package net.zepalesque.zenith.api.block.type;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class SpreadingPatchBlock extends Block implements BonemealableBlock {

    public static final MapCodec<SpreadingPatchBlock> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            propertiesCodec(),
            ResourceKey.codec(Registries.CONFIGURED_FEATURE).fieldOf("feature").forGetter(SpreadingPatchBlock::featureKey)
            ).apply(builder, SpreadingPatchBlock::new));

    @Override
    public MapCodec<SpreadingPatchBlock> codec() {
        return CODEC;
    }


    protected final ResourceKey<ConfiguredFeature<?, ?>> featureKey;
    public SpreadingPatchBlock(BlockBehaviour.Properties properties, ResourceKey<ConfiguredFeature<?, ?>> featureKey) {
        super(properties);
        this.featureKey = featureKey;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return level.getBlockState(pos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        level.registryAccess()
            .registry(Registries.CONFIGURED_FEATURE)
            .flatMap(reg -> reg.getHolder(this.featureKey()))
            .ifPresent(holder -> holder.value().place(level, level.getChunkSource().getGenerator(), random, pos.above()));
    }


    public ResourceKey<ConfiguredFeature<?, ?>> featureKey() {
        return featureKey;
    }

    @Override
    public BonemealableBlock.Type getType() {
        return BonemealableBlock.Type.NEIGHBOR_SPREADER;
    }
}