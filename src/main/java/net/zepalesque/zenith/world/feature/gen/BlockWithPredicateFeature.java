package net.zepalesque.zenith.world.feature.gen;

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
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class BlockWithPredicateFeature extends Feature<BlockWithPredicateFeature.Config> {
   public BlockWithPredicateFeature(Codec<Config> codec) {
      super(codec);
   }

   /**
    * Places the given feature at the given location.
    * During world generation, features are provided with a 3x3 region of chunks, centered on the chunk being generated,
    * that they can safely generate into.
    * @param context A context object with a reference to the level and the position the feature is being placed at
    */
   public boolean place(FeaturePlaceContext<Config> context) {
      Config config = context.config();
      WorldGenLevel level = context.level();
      BlockPos pos = context.origin();
      BlockState state = config.toPlace().getState(context.random(), pos);
      if (config.predicate().test(level, pos) && state.canSurvive(level, pos)) {
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

   public record Config(BlockStateProvider toPlace, BlockPredicate predicate) implements FeatureConfiguration {
       public static final Codec<Config> CODEC = RecordCodecBuilder.create((config) -> config.group(
               BlockStateProvider.CODEC.fieldOf("to_place").forGetter(Config::toPlace),
               BlockPredicate.CODEC.fieldOf("predicate").forGetter(Config::predicate)
       ).apply(config, Config::new));
   }
}