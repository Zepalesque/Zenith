package net.zepalesque.zenith.core.registry;

import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.zepalesque.zenith.core.Zenith;
import net.zepalesque.zenith.api.world.biome.modifier.ConditionalBiomeModifier;
import net.zepalesque.zenith.api.world.biome.modifier.FoliageModifier;
import net.zepalesque.zenith.api.world.biome.modifier.MusicModifier;
import net.zepalesque.zenith.api.world.biome.modifier.SkiesModifier;
import net.zepalesque.zenith.api.world.biome.modifier.WaterModifier;

@SuppressWarnings("unused")
public class ZenithBiomeModifiers {
    public static final DeferredRegister<MapCodec<? extends BiomeModifier>> CODECS = DeferredRegister.create(NeoForgeRegistries.BIOME_MODIFIER_SERIALIZERS, Zenith.MODID);

    public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<MusicModifier>> MUSIC = CODECS.register("modify_music", () -> MusicModifier.CODEC);
    public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<WaterModifier>> WATER_COLOR = CODECS.register("water_colors", () -> WaterModifier.CODEC);
    public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<SkiesModifier>> SKIES = CODECS.register("sky_colors", () -> SkiesModifier.CODEC);
    public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<FoliageModifier>> GRASS_FOLIAGE_COLOR = CODECS.register("foliage_colors", () -> FoliageModifier.CODEC);
    public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<ConditionalBiomeModifier>> CONDITIONAL_MODIFIER = CODECS.register("conditional", () -> ConditionalBiomeModifier.CODEC);
}
