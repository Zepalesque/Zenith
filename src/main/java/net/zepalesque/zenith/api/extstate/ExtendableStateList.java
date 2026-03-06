package net.zepalesque.zenith.api.extstate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.zenith.core.Zenith;
import net.zepalesque.zenith.core.registry.StateLists;

import java.util.Map;
import java.util.Optional;

import static net.zepalesque.zenith.core.registry.StateLists.STATE_LIST_MODIFIERS;

public class ExtendableStateList {
    private final int weightForDefaults;
    private final int totalWeight;
    // TODO: should this be blockstate providers instead of blockstates?
    private final SimpleWeightedRandomList<BlockState> defaults;

    public ExtendableStateList(int weightForDefaults, int weightForOthers, SimpleWeightedRandomList<BlockState> defaults) {
        this.weightForDefaults = weightForDefaults;
        this.defaults = defaults;
        this.totalWeight = weightForDefaults + weightForOthers;
    }

    public Optional<BlockState> calculate(RandomSource random, WorldGenLevel level, BlockPos pos) {
        if (this.totalWeight <= 0) return Optional.empty();
	    
	    var i = random.nextInt(totalWeight);
        if (i < this.weightForDefaults) return calculateDefaults(random);
        else return calculateOther(random, level, pos);

    }

    private Optional<BlockState> calculateDefaults(RandomSource random) {
        return this.defaults.getRandomValue(random);
    }

    private Optional<BlockState> calculateOther(RandomSource random, WorldGenLevel level, BlockPos pos) {
        var acc = level.registryAccess();
	    var reg = acc.registryOrThrow(Zenith.Keys.EXTENDABLE_STATE_LIST);
        var key = StateLists.STATE_LIST_REGISTRY.getResourceKey(this).orElseThrow();
        var entries = reg.getDataMap(STATE_LIST_MODIFIERS).get(key);
        if (entries.size() != 0) {
	        var length = entries.size();
	        var index = random.nextInt(length);
            return entries.get(index).value().calculate(random, level, pos);
        }
        return Optional.empty();
    }

    public record Entry(Optional<Map<ResourceKey<Biome>, SimpleWeightedRandomList<BlockState>>> byBiome, Optional<SimpleWeightedRandomList<BlockState>> fallback) {
        
        
        public static final Codec<Holder<Entry>> CODEC = RegistryFileCodec.create(Zenith.Keys.EXTENDABLE_STATE_LIST_ENTRY, Entry.DIRECT);
        public static final Codec<HolderSet<Entry>> LIST_CODEC = RegistryCodecs.homogeneousList(Zenith.Keys.EXTENDABLE_STATE_LIST_ENTRY, Entry.DIRECT);
        public static final Codec<Entry> DIRECT = RecordCodecBuilder.create(builder -> builder.group(
            Codec.unboundedMap(ResourceKey.codec(Registries.BIOME), SimpleWeightedRandomList.wrappedCodec(BlockState.CODEC)).optionalFieldOf("by_biome").forGetter(Entry::byBiome),
            SimpleWeightedRandomList.wrappedCodec(BlockState.CODEC).optionalFieldOf("fallback").forGetter(Entry::fallback)
        ).apply(builder, Entry::new));


        public Optional<BlockState> calculate(RandomSource random, WorldGenLevel level, BlockPos pos) {
	        var biome = level.getBiome(pos);
	        var optional = biome.unwrapKey();
            if (optional.isPresent()) {
	            var key = optional.get();
                if (this.byBiome.isPresent() && this.byBiome.get().containsKey(key))
                    return this.byBiome.get()
                        .get(key)
                        .getRandomValue(random);
            }
            return this.fallback
                .flatMap(list -> list.getRandomValue(random));
        }

    }
}
