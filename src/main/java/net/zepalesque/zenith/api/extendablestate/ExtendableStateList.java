package net.zepalesque.zenith.api.extendablestate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.zenith.core.registry.StateLists;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ExtendableStateList {
    private final int weightForDefaults;
    private final int totalWeight;
    private final SimpleWeightedRandomList<BlockState> defaults;
    private final List<Entry> entries = new ArrayList<>();

    public ExtendableStateList(int weightForDefaults, int weightForOthers, SimpleWeightedRandomList<BlockState> defaults) {
        this.weightForDefaults = weightForDefaults;
        this.defaults = defaults;
        this.totalWeight = weightForDefaults + weightForOthers;
    }

    public BlockState calculate(RandomSource random, WorldGenLevel level, BlockPos pos) {
        if (this.totalWeight <= 0) return Blocks.AIR.defaultBlockState();

        int i = random.nextInt(totalWeight);
        if (i < this.weightForDefaults) return calculateDefaults(random, level, pos);
        else return calculateOther(random, level, pos);

    }

    private BlockState calculateDefaults(RandomSource random, WorldGenLevel level, BlockPos pos) {
        return this.defaults.getRandomValue(random).orElseThrow(IllegalStateException::new);
    }

    private BlockState calculateOther(RandomSource random, WorldGenLevel level, BlockPos pos) {
        if (!this.entries.isEmpty()) {
            int length = this.entries.size();
            int index = random.nextInt(length);
            return this.entries.get(index).calculate(random, level, pos);
        }
        return Blocks.AIR.defaultBlockState();
    }

    public record Entry(ExtendableStateList list, Optional<Map<ResourceKey<Biome>, SimpleWeightedRandomList<BlockState>>> byBiome, Optional<SimpleWeightedRandomList<BlockState>> fallback) {

        private static boolean allowCreation = false;

        
        public Entry {
            if (!allowCreation) throw new AssertionError("Use ExtendableStateList$Entry#create please!");
        }

        public static Entry create(ExtendableStateList list, Optional<Map<ResourceKey<Biome>, SimpleWeightedRandomList<BlockState>>> byBiome, Optional<SimpleWeightedRandomList<BlockState>> fallback) {
            allowCreation = true;
            Entry e = new Entry(list, byBiome, fallback);
            list.entries.add(e);
            allowCreation = false;
            return e;
        }

        public static Codec<Entry> CODEC = RecordCodecBuilder.create(builder -> builder.group(
                StateLists.STATE_LIST_REGISTRY.byNameCodec().fieldOf("parent_state_list").forGetter(Entry::list),
                Codec.unboundedMap(ResourceKey.codec(Registries.BIOME), SimpleWeightedRandomList.wrappedCodec(BlockState.CODEC)).optionalFieldOf("by_biome").forGetter(Entry::byBiome),
                SimpleWeightedRandomList.wrappedCodec(BlockState.CODEC).optionalFieldOf("fallback").forGetter(Entry::fallback)
        ).apply(builder, Entry::create));


        public BlockState calculate(RandomSource random, WorldGenLevel level, BlockPos pos) {
            Holder<Biome> biome = level.getBiome(pos);
            Optional<ResourceKey<Biome>> optional = biome.unwrapKey();
            if (optional.isPresent()) {
                ResourceKey<Biome> key = optional.get();
                if (this.byBiome.isPresent() && this.byBiome.get().containsKey(key)) {
                    return this.byBiome.get().get(key).getRandomValue(random).orElseThrow(IllegalStateException::new);
                }
            }
            return this.fallback.isEmpty() ? Blocks.AIR.defaultBlockState() : this.fallback.get().getRandomValue(random).orElseThrow(IllegalStateException::new);
        }

    }
}
