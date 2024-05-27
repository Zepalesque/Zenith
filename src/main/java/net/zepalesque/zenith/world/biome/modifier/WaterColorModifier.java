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

public record WaterColorModifier(
        HolderSet<Biome> biomes,
        CodecPredicates.DualInt predicate,
        int water, int fog,
        Optional<Holder<Condition<?>>> condition) implements BiomeModifier {

    public static final Codec<WaterColorModifier> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Biome.LIST_CODEC.fieldOf("biomes").forGetter(WaterColorModifier::biomes),
            WaterColorModifier.WATER_PREDICATE.fieldOf("predicate").forGetter(WaterColorModifier::predicate),
            Codec.INT.fieldOf("water_color").forGetter(WaterColorModifier::water),
            Codec.INT.fieldOf("water_fog_color").forGetter(WaterColorModifier::fog), 
            Condition.CODEC.optionalFieldOf("condition").forGetter(WaterColorModifier::condition))
            .apply(builder, WaterColorModifier::new));

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.AFTER_EVERYTHING &&
                biomes.contains(biome) &&
                (condition.isEmpty() || (!condition.get().isBound() || condition.get().value().test())) &&
                predicate.test(builder.getSpecialEffects().waterColor(), builder.getSpecialEffects().getWaterFogColor()))
        { builder.getSpecialEffects().waterColor(water).waterFogColor(fog); }

    }

    public static Codec<CodecPredicates.DualInt> WATER_PREDICATE = CodecPredicates.DualInt.createCodec("water", "fog");
    
    @Override
    public Codec<? extends BiomeModifier> codec() {
        return CODEC;
    }
}
