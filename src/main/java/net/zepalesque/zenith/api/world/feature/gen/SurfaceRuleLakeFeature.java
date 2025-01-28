package net.zepalesque.zenith.api.world.feature.gen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.zepalesque.zenith.mixin.mixins.common.accessor.ChunkAccessAccessor;

import java.util.Optional;

public class SurfaceRuleLakeFeature extends Feature<SurfaceRuleLakeFeature.Config> {
    private static final BlockState AIR;

    public SurfaceRuleLakeFeature(Codec<Config> p_66259_) {
        super(p_66259_);
    }

    @SuppressWarnings("deprecation")
    public boolean place(FeaturePlaceContext<Config> context) {
        BlockPos blockpos = context.origin();
        WorldGenLevel worldgenlevel = context.level();
        RandomSource random = context.random();
        Config config = context.config();
        if (blockpos.getY() <= worldgenlevel.getMinBuildHeight() + 4) {
            return false;
        } else {
            blockpos = blockpos.below(4);
            boolean[] shouldPlace = new boolean[2048];
            int tries = random.nextInt(4) + 4;

            for(int i = 0; i < tries; ++i) {
                double xDiv = random.nextDouble() * 6.0 + 3.0;
                double yDiv = random.nextDouble() * 4.0 + 2.0;
                double zDiv = random.nextDouble() * 6.0 + 3.0;
                double xReduce = random.nextDouble() * (16.0 - xDiv - 2.0) + 1.0 + xDiv / 2.0;
                double yReduce = random.nextDouble() * (8.0 - yDiv - 4.0) + 2.0 + yDiv / 2.0;
                double zReduce = random.nextDouble() * (16.0 - zDiv - 2.0) + 1.0 + zDiv / 2.0;

                for(int xTest = 1; xTest < 15; ++xTest) {
                    for(int zTest = 1; zTest < 15; ++zTest) {
                        for(int yTest = 1; yTest < 7; ++yTest) {
                            double xBounded = ((double)xTest - xReduce) / (xDiv / 2.0);
                            double ySize = ((double)yTest - yReduce) / (yDiv / 2.0);
                            double zSize = ((double)zTest - zReduce) / (zDiv / 2.0);
                            double sizeSquare = xBounded * xBounded + ySize * ySize + zSize * zSize;
                            if (sizeSquare < 1.0) {
                                shouldPlace[packPos(xTest, yTest, zTest)] = true;
                            }
                        }
                    }
                }
            }

            BlockState blockstate1 = config.fluid().getState(random, blockpos);

            int x;
            int y;
            int z;
            for(x = 0; x < 16; ++x) {
                for(z = 0; z < 16; ++z) {
                    for(y = 0; y < 8; ++y) {
                        boolean flag = !shouldPlace[packPos(x, y, z)] && (x < 15 && shouldPlace[packPos(x + 1, y, z)] || x > 0 && shouldPlace[packPos(x - 1, y, z)] || z < 15 && shouldPlace[packPos(x, y, z + 1)] || z > 0 && shouldPlace[packPos(x, y, z - 1)] || y < 7 && shouldPlace[packPos(x, y + 1, z)] || y > 0 && shouldPlace[packPos(x, y - 1, z)]);
                        if (flag) {
                            BlockState testState = worldgenlevel.getBlockState(blockpos.offset(x, y, z));
                            if (y >= 4 && testState.liquid()) {
                                return false;
                            }

                            if (y < 4 && !testState.isSolid() && worldgenlevel.getBlockState(blockpos.offset(x, y, z)) != blockstate1) {
                                return false;
                            }
                        }
                    }
                }
            }

            boolean doFloor = config.floor().isPresent();

            BlockPos blockpos2, flootPos;
            for(x = 0; x < 16; ++x) {
                for(z = 0; z < 16; ++z) {
                    boolean doneFloorThisRow = false;
                    for(y = 0; y < 8; ++y) {
                        if (shouldPlace[packPos(x, y, z)]) {
                            blockpos2 = blockpos.offset(x, y, z);
                            if (this.canReplaceBlock(worldgenlevel.getBlockState(blockpos2))) {

                                if (doFloor && !doneFloorThisRow) {
                                    flootPos = blockpos2.below();
                                    if (this.canReplaceBlock(worldgenlevel.getBlockState(flootPos))) {
                                        worldgenlevel.setBlock(flootPos, config.floor().get().getState(worldgenlevel, random, blockpos), 2);
                                    }
                                    doneFloorThisRow = true;
                                }

                                boolean flag1 = y >= 4;
                                worldgenlevel.setBlock(blockpos2, flag1 ? AIR : blockstate1, 2);
                                if (flag1) {
                                    worldgenlevel.scheduleTick(blockpos2, AIR.getBlock(), 0);
                                    this.markAboveForPostProcessing(worldgenlevel, blockpos2);
                                }
                            }
                        }
                    }
                }
            }

                for(z = 0; z < 16; ++z) {
                    for(y = 0; y < 16; ++y) {
                        for(int j4 = 4; j4 < 8; ++j4) {
                            if (shouldPlace[packPos(z, j4, y)]) {
                                BlockPos blockpos3 = blockpos.offset(z, j4 - 1, y);
                                if (isDirt(worldgenlevel.getBlockState(blockpos3)) && worldgenlevel.getBrightness(LightLayer.SKY, blockpos.offset(z, j4, y)) > 0) {
                                    if (context.level().getChunkSource() instanceof ServerChunkCache serverChunkCache) {
                                        if (serverChunkCache.getGenerator() instanceof NoiseBasedChunkGenerator noiseBasedChunkGenerator) {
                                            NoiseGeneratorSettings settingsHolder = noiseBasedChunkGenerator.generatorSettings().value();
                                            SurfaceRules.RuleSource surfaceRule = settingsHolder.surfaceRule();
                                            ChunkAccess chunkAccess = context.level().getChunk(blockpos3);
                                            NoiseChunk noisechunk = ((ChunkAccessAccessor) chunkAccess).getNoiseChunk();
                                            if (noisechunk != null) {
                                                CarvingContext carvingcontext = new CarvingContext(noiseBasedChunkGenerator, context.level().registryAccess(), chunkAccess.getHeightAccessorForGeneration(), noisechunk, serverChunkCache.randomState(), surfaceRule);
                                                Optional<BlockState> state = carvingcontext.topMaterial(context.level().getBiomeManager()::getBiome, chunkAccess, blockpos3, false);
                                                state.ifPresent(blockState -> worldgenlevel.setBlock(blockpos3, blockState, 2));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            if (blockstate1.getFluidState().is(FluidTags.WATER)) {
                for(x = 0; x < 16; ++x) {
                    for(z = 0; z < 16; ++z) {
                        blockpos2 = blockpos.offset(x, 4, z);
                        if (worldgenlevel.getBiome(blockpos2).value().shouldFreeze(worldgenlevel, blockpos2, false) && this.canReplaceBlock(worldgenlevel.getBlockState(blockpos2))) {
                            worldgenlevel.setBlock(blockpos2, Blocks.ICE.defaultBlockState(), 2);
                        }
                    }
                }
            }

            return true;
        }
    }

    // TODO: look into the specific way this works
    private static int packPos(int x, int y, int z) {
        return (x * 16 + z) * 8 + y;
    }

    private boolean canReplaceBlock(BlockState state) {
        return !state.is(BlockTags.FEATURES_CANNOT_REPLACE);
    }

    static {
        AIR = Blocks.CAVE_AIR.defaultBlockState();
    }

    public record Config(BlockStateProvider fluid, Optional<RuleBasedBlockStateProvider> floor) implements FeatureConfiguration {
        public static final Codec<Config> CODEC = RecordCodecBuilder.create(builder -> builder.group(
                BlockStateProvider.CODEC.fieldOf("fluid").forGetter(Config::fluid),
                RuleBasedBlockStateProvider.CODEC.optionalFieldOf("floor").forGetter(Config::floor)
        ).apply(builder, Config::new));
    }
}
