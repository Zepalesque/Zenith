package net.zepalesque.zenith.world.feature.gen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class LargeRockFeature extends Feature<LargeRockFeature.Config> {

    public LargeRockFeature(Codec<Config> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<Config> context) {
        BlockPos origin = context.origin();
        WorldGenLevel level = context.level();
        RandomSource rand = context.random();
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        // Place main pillar
        for (int i = -1; i < 4; i++) {
            mutable.setWithOffset(origin, 0, i, 0);
            setBlock(mutable, context);
        }
        Collection<Direction> potentiallyPlaceAbove = new ArrayList<>();
        Collection<Direction> placedAbove = new ArrayList<>();
        for (Direction d : Direction.Plane.HORIZONTAL) {
            // Places corner pieces, stores directions next to them for potential height bumps
            if (rand.nextFloat() < 0.7) {
                BlockPos imm = origin.relative(d).relative(d.getCounterClockWise());
                setBlock(imm, context);
                for (Direction d1 : Direction.Plane.VERTICAL) {
                    if (rand.nextFloat() < 0.5) {
                        mutable.setWithOffset(imm, d1);
                        setBlock(mutable, context);
                        if (d1 == Direction.UP) {
                            potentiallyPlaceAbove.add(d);
                            potentiallyPlaceAbove.add(d.getCounterClockWise());
                        }
                    }
                }
            }
            // Keep a list of directions that actually had a chunk placed above
            for (int i = -1; i < 3; i++) {
                BlockPos imm1 = origin.relative(d);
                if (i < 2 || (potentiallyPlaceAbove.contains(d) && rand.nextFloat() < 0.7)) {
                    setBlock(imm1.above(i), context);
                    placedAbove.add(d);
                }
            }
        }
        int count = placedAbove.size();
        if (count > 1) {
            float chance = count == 2 ? 0.3F : count == 3 ? 0.7F : 1;
            if (count > 3 || rand.nextFloat() < chance) {
                setBlock(origin.above(4), context);
            }
        }

        if (rand.nextFloat() < 0.25) {
            Direction d1 = Direction.Plane.HORIZONTAL.getRandomDirection(rand);
            Direction d2 = rand.nextBoolean() ? d1.getClockWise() : d1.getCounterClockWise();
            BlockPos pos = origin.relative(d1, 2).relative(d2);
            BlockPos below = pos.below();
            BlockPos underBelow = below.below();
            if (level.isStateAtPosition(pos, state -> state.isFaceSturdy(level, pos, Direction.UP))) {
                placeSecondChunk(pos.above(), mutable, context);
            } else if (level.isStateAtPosition(below, state -> state.isFaceSturdy(level, below, Direction.UP))) {
                placeSecondChunk(pos, mutable, context);
            } else if (level.isStateAtPosition(underBelow, state -> state.isFaceSturdy(level, underBelow, Direction.UP))) {
                placeSecondChunk(below, mutable, context);
            }
        }
        return true;
    }

    private void placeSecondChunk(BlockPos pos, BlockPos.MutableBlockPos mutable, FeaturePlaceContext<Config> context) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    int total = Math.abs(x) + Math.abs(y) + Math.abs(z);
                    boolean place = context.random().nextBoolean() ? total <= 1 : total <= 2;
                    if (place) {
                        mutable.setWithOffset(pos, x, y, z);
                        setBlock(mutable, context);
                    }
                }
            }
        }
    }

    private static void setBlock(BlockPos pos, FeaturePlaceContext<Config> context) {
        WorldGenLevel level = context.level();
        Config config = context.config();
        BlockStateProvider provider = config.block();
        RandomSource rand = context.random();
        Optional<HolderSet<Block>> optional = config.replaceableStates();
        if (level.isStateAtPosition(pos, bs -> bs.isAir() || bs.is(BlockTags.REPLACEABLE) || (optional.isPresent() && bs.is(optional.get())))) {
            level.setBlock(pos, provider.getState(rand, pos), 2);
        }
    }

    public record Config(BlockStateProvider block, Optional<HolderSet<Block>> replaceableStates) implements FeatureConfiguration {
        public static final Codec<Config> CODEC = RecordCodecBuilder.create((config) -> config.group(
                BlockStateProvider.CODEC.fieldOf("block").forGetter(Config::block),
                RegistryCodecs.homogeneousList(Registries.BLOCK).optionalFieldOf("replaceable_states").forGetter(Config::replaceableStates)
        ).apply(config, Config::new));
    }
}
