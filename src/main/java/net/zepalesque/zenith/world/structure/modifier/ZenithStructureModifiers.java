package net.zepalesque.zenith.world.structure.modifier;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.StructureModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.zepalesque.zenith.Zenith;
import net.zepalesque.zenith.world.biome.modifier.ConditionalBiomeModifier;
import net.zepalesque.zenith.world.biome.modifier.GrassFoliageModifier;
import net.zepalesque.zenith.world.biome.modifier.MusicModifier;
import net.zepalesque.zenith.world.biome.modifier.SkyModifier;
import net.zepalesque.zenith.world.biome.modifier.WaterColorModifier;

public class ZenithStructureModifiers {
    public static final DeferredRegister<Codec<? extends StructureModifier>> CODECS = DeferredRegister.create(NeoForgeRegistries.STRUCTURE_MODIFIER_SERIALIZERS, Zenith.MODID);

    public static final DeferredHolder<Codec<? extends StructureModifier>, Codec<ConditionalStructureModifier>> CONDITIONAL_MODIFIER = CODECS.register("conditional", () -> ConditionalStructureModifier.CODEC);
    public static final DeferredHolder<Codec<? extends StructureModifier>, Codec<RemoveStructureModifier>> REMOVE = CODECS.register("remove_structure", () -> RemoveStructureModifier.CODEC);
}
