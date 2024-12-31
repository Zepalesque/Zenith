package net.zepalesque.zenith.api.world.structure.modifier;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.neoforged.neoforge.common.world.ModifiableStructureInfo;
import net.neoforged.neoforge.common.world.StructureModifier;
import net.zepalesque.zenith.core.Zenith;
import net.zepalesque.zenith.api.condition.Condition;

public record ConditionalStructureModifier(Holder<StructureModifier> modifier, Holder<Condition<?>> condition) implements StructureModifier {

    public static final MapCodec<ConditionalStructureModifier> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            StructureModifier.REFERENCE_CODEC.fieldOf("modify").forGetter(ConditionalStructureModifier::modifier),
            Condition.CODEC.fieldOf("when").forGetter(ConditionalStructureModifier::condition)
    ).apply(builder, ConditionalStructureModifier::new));


    @Override
    public void modify(Holder<Structure> structure, Phase phase, ModifiableStructureInfo.StructureInfo.Builder builder) {
        if (!this.condition().isBound()) {
            Zenith.LOGGER.error("Failed to conditionally modify structure!");
            return;
        }
        if (!this.modifier().isBound()) {
            Zenith.LOGGER.error("Failed to conditionally modify structure!");
            return;
        }

        if (this.condition().value().test()) {
            this.modifier.value().modify(structure, phase, builder);
        }
    }

    @Override
    public MapCodec<? extends StructureModifier> codec() {
        return CODEC;
    }
}
