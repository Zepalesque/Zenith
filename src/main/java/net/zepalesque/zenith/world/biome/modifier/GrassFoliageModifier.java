package net.zepalesque.zenith.world.biome.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;

public record GrassFoliageModifier(HolderSet<Biome> biomes, int grass, int foliage) implements BiomeModifier {
    
    public static final Codec<GrassFoliageModifier> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Biome.LIST_CODEC.fieldOf("biomes").forGetter(GrassFoliageModifier::biomes),
            Codec.INT.fieldOf("grass_color").forGetter(GrassFoliageModifier::grass),
            Codec.INT.fieldOf("foliage_color").forGetter(GrassFoliageModifier::foliage)
    ).apply(builder, GrassFoliageModifier::new));
    
    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.AFTER_EVERYTHING && biomes.contains(biome)) {
            builder.getSpecialEffects().grassColorOverride(grass);
            builder.getSpecialEffects().foliageColorOverride(foliage);
        }

    }
    @Override
    public Codec<? extends BiomeModifier> codec() {
        return CODEC;
    }
}
