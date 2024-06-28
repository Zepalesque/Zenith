package net.zepalesque.zenith.world.biome.modifier;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.zepalesque.zenith.util.codec.ZenithCodecs;

import java.util.Map;
import java.util.Optional;

public record WaterModifier(Optional<DefaultWaterSettings> settings, Map<Holder<Biome>, Integer> waterMap, Map<Holder<Biome>, Integer> fogMap) implements BiomeModifier {

    public static final Codec<WaterModifier> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            DefaultWaterSettings.CODEC.optionalFieldOf("default_colors").forGetter(WaterModifier::settings),
            ZenithCodecs.MAP_CODEC.fieldOf("water_map").forGetter(WaterModifier::waterMap),
            ZenithCodecs.MAP_CODEC.fieldOf("fog_map").forGetter(WaterModifier::fogMap)).apply(builder, WaterModifier::new));


    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.AFTER_EVERYTHING) {
            if (settings.isEmpty() || settings.get().biomes.contains(biome)) {
                if (settings.isPresent()) {
                    settings.get().water.ifPresent(builder.getSpecialEffects()::waterColor);
                    settings.get().fog.ifPresent(builder.getSpecialEffects()::waterFogColor);
                }

                if (waterMap.containsKey(biome)) {
                    builder.getSpecialEffects().waterColor(waterMap.get(biome));
                }

                if (fogMap.containsKey(biome)) {
                    builder.getSpecialEffects().waterFogColor(fogMap.get(biome));
                }
            }
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return CODEC;
    }


    public record DefaultWaterSettings(HolderSet<Biome> biomes, Optional<Integer> water, Optional<Integer> fog) {
        public static final Codec<DefaultWaterSettings> CODEC = RecordCodecBuilder.create(builder -> builder.group(
                Biome.LIST_CODEC.fieldOf("biomes").forGetter(DefaultWaterSettings::biomes),
                Codec.INT.optionalFieldOf("water").forGetter(DefaultWaterSettings::water),
                Codec.INT.optionalFieldOf("fog").forGetter(DefaultWaterSettings::fog)
                ).apply(builder, DefaultWaterSettings::new));
    }
}
