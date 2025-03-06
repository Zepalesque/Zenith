package net.zepalesque.zenith.core.registry;

import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.world.StructureModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.zepalesque.zenith.core.Zenith;
import net.zepalesque.zenith.api.world.structure.modifier.ConditionalStructureModifier;
import net.zepalesque.zenith.api.world.structure.modifier.RemoveStructureModifier;

@SuppressWarnings("unused")
public class ZenithStructureModifiers {
    public static final DeferredRegister<MapCodec<? extends StructureModifier>> CODECS = DeferredRegister.create(NeoForgeRegistries.STRUCTURE_MODIFIER_SERIALIZERS, Zenith.MODID);

    public static final DeferredHolder<MapCodec<? extends StructureModifier>, MapCodec<ConditionalStructureModifier>> CONDITIONAL_MODIFIER = CODECS.register("conditional", () -> ConditionalStructureModifier.CODEC);
    public static final DeferredHolder<MapCodec<? extends StructureModifier>, MapCodec<RemoveStructureModifier>> REMOVE = CODECS.register("remove_structure", () -> RemoveStructureModifier.CODEC);
}