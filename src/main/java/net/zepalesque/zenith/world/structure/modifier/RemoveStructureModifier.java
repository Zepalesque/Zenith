package net.zepalesque.zenith.world.structure.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.neoforged.neoforge.common.world.ModifiableStructureInfo;
import net.neoforged.neoforge.common.world.StructureModifier;
import net.zepalesque.zenith.Zenith;
import net.zepalesque.zenith.api.condition.Condition;

import java.sql.Struct;

public record RemoveStructureModifier(HolderSet<Structure> structures) implements StructureModifier {

    private static final Codec<HolderSet<Structure>> STRUCTURE_LIST = RegistryCodecs.homogeneousList(Registries.STRUCTURE, Structure.DIRECT_CODEC);

    public static final Codec<RemoveStructureModifier> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            STRUCTURE_LIST.fieldOf("structures").forGetter(RemoveStructureModifier::structures)
    ).apply(builder, RemoveStructureModifier::new));

    @Override
    public void modify(Holder<Structure> structure, Phase phase, ModifiableStructureInfo.StructureInfo.Builder builder) {
        if (phase == Phase.AFTER_EVERYTHING && this.structures.contains(structure)) {
            builder.getStructureSettings().setBiomes(HolderSet.direct());
        }
    }

    @Override
    public Codec<? extends StructureModifier> codec() {
        return CODEC;
    }
}
