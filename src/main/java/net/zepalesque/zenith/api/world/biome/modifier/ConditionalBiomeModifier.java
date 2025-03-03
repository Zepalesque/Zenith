package net.zepalesque.zenith.api.world.biome.modifier;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.zepalesque.zenith.core.Zenith;
import net.zepalesque.zenith.api.condition.Condition;

public record ConditionalBiomeModifier(Holder<BiomeModifier> modifier, Holder<Condition<?>> condition) implements BiomeModifier {
    
    public static final MapCodec<ConditionalBiomeModifier> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            BiomeModifier.REFERENCE_CODEC.fieldOf("modify").forGetter(ConditionalBiomeModifier::modifier),
            Condition.CODEC.fieldOf("when").forGetter(ConditionalBiomeModifier::condition)
    ).apply(builder, ConditionalBiomeModifier::new));
    
    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (!this.condition().isBound()) {
            Zenith.LOGGER.error("Failed to conditionally modify biome!");
            return;
        }
        if (!this.modifier().isBound()) {
            Zenith.LOGGER.error("Failed to conditionally modify biome!");
            return;
        }

        if (this.condition().value().test()) this.modifier.value().modify(biome, phase, builder);
    }
    @Override
    public MapCodec<? extends BiomeModifier> codec() {
        return CODEC;
    }
}
