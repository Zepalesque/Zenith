package net.zepalesque.zenith.api.world.biome.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.zepalesque.zenith.api.serialization.codec.MoreCodecs;

import java.util.Map;
import java.util.Optional;

public record FoliageModifier(Optional<DefaultFoliageSettings> settings, Map<Holder<Biome>, Integer> grassMap, Map<Holder<Biome>, Integer> foliageMap) implements BiomeModifier {

    public static final MapCodec<FoliageModifier> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            DefaultFoliageSettings.CODEC.optionalFieldOf("default_colors").forGetter(FoliageModifier::settings),
            MoreCodecs.BIOME_COLOR_MAP.fieldOf("grass_map").forGetter(FoliageModifier::grassMap),
            MoreCodecs.BIOME_COLOR_MAP.fieldOf("foliage_map").forGetter(FoliageModifier::foliageMap)).apply(builder, FoliageModifier::new));


    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.AFTER_EVERYTHING) {
            if (settings.isEmpty() || settings.get().biomes.contains(biome)) {
                if (settings.isPresent()) {
                    settings.get().grass.ifPresent(builder.getSpecialEffects()::grassColorOverride);
                    settings.get().foliage.ifPresent(builder.getSpecialEffects()::foliageColorOverride);
                }

                if (grassMap.containsKey(biome)) {
                    builder.getSpecialEffects().grassColorOverride(grassMap.get(biome));
                }

                if (foliageMap.containsKey(biome)) {
                    builder.getSpecialEffects().foliageColorOverride(foliageMap.get(biome));
                }
            }
        }
    }

    @Override
    public MapCodec<? extends BiomeModifier> codec() {
        return CODEC;
    }


    public record DefaultFoliageSettings(HolderSet<Biome> biomes, Optional<Integer> grass, Optional<Integer> foliage) {
        public static final Codec<DefaultFoliageSettings> CODEC = RecordCodecBuilder.create(builder -> builder.group(
                Biome.LIST_CODEC.fieldOf("biomes").forGetter(DefaultFoliageSettings::biomes),
                Codec.INT.optionalFieldOf("grass").forGetter(DefaultFoliageSettings::grass),
                Codec.INT.optionalFieldOf("foliage").forGetter(DefaultFoliageSettings::foliage)
                ).apply(builder, DefaultFoliageSettings::new));
    }
}
