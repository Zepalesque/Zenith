package net.zepalesque.zenith.api.condition;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.ExtraCodecs;
import net.zepalesque.zenith.Zenith;

import java.util.function.Function;

public interface Condition<T extends Condition<?>> {

    Codec<Condition<?>> ELEMENT_CODEC = ExtraCodecs.lazyInitializedCodec(
            () -> ConditionElements.ELEMENT_REGISTRY.byNameCodec().dispatch("condition", Condition::codec, Function.identity()));

    Codec<Holder<Condition<?>>> CODEC = RegistryFileCodec.create(Zenith.Keys.CONDITION, ELEMENT_CODEC);

    boolean test();

    Codec<T> codec();

}
