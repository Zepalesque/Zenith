package net.zepalesque.zenith.api.world.feature.gen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.zepalesque.zenith.api.extendablestate.ExtendableStateList;
import net.zepalesque.zenith.core.registry.StateLists;

import java.util.Optional;

public class ExtendableStateListBlockFeature extends Feature<ExtendableStateListBlockFeature.Config> {

    public ExtendableStateListBlockFeature(Codec<Config> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<ExtendableStateListBlockFeature.Config> context) {
        ExtendableStateListBlockFeature.Config config = context.config();
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        Optional<BlockPredicate> predicate = context.config().predicate();
        BlockState state = config.list().calculate(context.random(), context.level(), pos);
        if ((predicate.isEmpty() || predicate.get().test(level, pos)) && state.canSurvive(level, pos)) {
            if (state.getBlock() instanceof DoublePlantBlock) {
                if (!level.isEmptyBlock(pos.above())) {
                    return false;
                }

                DoublePlantBlock.placeAt(level, state, pos, 2);
            } else {
                level.setBlock(pos, state, 2);
            }

            return true;
        } else {
            return false;
        }
    }


    public record Config(ExtendableStateList list, Optional<BlockPredicate> predicate) implements FeatureConfiguration {
        public static final Codec<Config> CODEC = RecordCodecBuilder.create((config) -> config.group(
                StateLists.STATE_LIST_REGISTRY.byNameCodec().fieldOf("to_place").forGetter(Config::list),
                BlockPredicate.CODEC.optionalFieldOf("predicate").forGetter(Config::predicate)
        ).apply(config, Config::new));
    }
}
