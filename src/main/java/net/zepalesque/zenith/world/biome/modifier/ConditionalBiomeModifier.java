package net.zepalesque.zenith.world.biome.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.zepalesque.zenith.Zenith;
import net.zepalesque.zenith.api.condition.Condition;

public record ConditionalBiomeModifier(Holder<BiomeModifier> modifier, Holder<Condition<?>> condition) implements BiomeModifier {
    
    public static final Codec<ConditionalBiomeModifier> CODEC = RecordCodecBuilder.create(builder -> builder.group(
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

        if (this.condition().value().test()) {
            this.modifier.value().modify(biome, phase, builder);
        }
    }
    @Override
    public Codec<? extends BiomeModifier> codec() {
        return CODEC;
    }
}
