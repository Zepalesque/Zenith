package net.zepalesque.zenith.world.biome.modifier;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.zepalesque.zenith.Zenith;

public class ZenithModifiers {
    public static final DeferredRegister<Codec<? extends BiomeModifier>> CODECS = DeferredRegister.create(NeoForgeRegistries.BIOME_MODIFIER_SERIALIZERS, Zenith.MODID);

    public static final DeferredHolder<Codec<? extends BiomeModifier>, Codec<MusicModifier>> MUSIC = CODECS.register("music", () -> MusicModifier.CODEC);
    public static final DeferredHolder<Codec<? extends BiomeModifier>, Codec<WaterColorModifier>> WATER_COLOR = CODECS.register("water_color", () -> WaterColorModifier.CODEC);
    public static final DeferredHolder<Codec<? extends BiomeModifier>, Codec<SkyModifier>> SKY_COLOR = CODECS.register("sky_color", () -> SkyModifier.CODEC);
    public static final DeferredHolder<Codec<? extends BiomeModifier>, Codec<GrassFoliageModifier>> GRASS_FOLIAGE_COLOR = CODECS.register("grass_foliage_color", () -> GrassFoliageModifier.CODEC);
}
