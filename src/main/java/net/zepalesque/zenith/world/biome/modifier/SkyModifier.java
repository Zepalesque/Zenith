package net.zepalesque.zenith.world.biome.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.zepalesque.zenith.api.condition.Condition;
import net.zepalesque.zenith.util.codec.CodecPredicates;

import java.util.Optional;

public record SkyModifier(HolderSet<Biome> biomes,
                          CodecPredicates.DualInt predicate,
                          int sky, int fog,
                          Optional<Holder<Condition<?>>> condition) implements BiomeModifier {

    public static final Codec<SkyModifier> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Biome.LIST_CODEC.fieldOf("biomes").forGetter(SkyModifier::biomes),
            SkyModifier.SKY_PREDICATE.fieldOf("predicate").forGetter(SkyModifier::predicate),
            Codec.INT.fieldOf("water_color").forGetter(SkyModifier::sky),
            Codec.INT.fieldOf("water_fog_color").forGetter(SkyModifier::fog),
            Condition.CODEC.optionalFieldOf("condition").forGetter(SkyModifier::condition)
    ).apply(builder, SkyModifier::new));

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.AFTER_EVERYTHING &&
                biomes.contains(biome) &&
                (condition.isEmpty() || (!condition.get().isBound() || condition.get().value().test())) &&
                predicate.test(builder.getSpecialEffects().getSkyColor(), builder.getSpecialEffects().getFogColor())) {
            builder.getSpecialEffects().skyColor(sky);
            builder.getSpecialEffects().fogColor(fog);
        }

    }

    public static Codec<CodecPredicates.DualInt> SKY_PREDICATE = CodecPredicates.DualInt.createCodec("sky", "fog");

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return CODEC;
    }
}
