package net.zepalesque.zenith.world.biome.modifier;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.zepalesque.zenith.Zenith;

public class ZenithBiomeModifiers {
    public static final DeferredRegister<Codec<? extends BiomeModifier>> CODECS = DeferredRegister.create(NeoForgeRegistries.BIOME_MODIFIER_SERIALIZERS, Zenith.MODID);

    public static final DeferredHolder<Codec<? extends BiomeModifier>, Codec<MusicModifier>> MUSIC = CODECS.register("modify_music", () -> MusicModifier.CODEC);
    public static final DeferredHolder<Codec<? extends BiomeModifier>, Codec<WaterModifier>> WATER_COLOR = CODECS.register("water_colors", () -> WaterModifier.CODEC);
    public static final DeferredHolder<Codec<? extends BiomeModifier>, Codec<SkiesModifier>> SKIES = CODECS.register("sky_colors", () -> SkiesModifier.CODEC);
    public static final DeferredHolder<Codec<? extends BiomeModifier>, Codec<FoliageModifier>> GRASS_FOLIAGE_COLOR = CODECS.register("foliage_colors", () -> FoliageModifier.CODEC);
    public static final DeferredHolder<Codec<? extends BiomeModifier>, Codec<ConditionalBiomeModifier>> CONDITIONAL_MODIFIER = CODECS.register("conditional", () -> ConditionalBiomeModifier.CODEC);
}
