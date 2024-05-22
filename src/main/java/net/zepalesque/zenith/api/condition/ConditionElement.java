package net.zepalesque.zenith.api.condition;

import com.mojang.serialization.Codec;
import net.minecraft.util.ExtraCodecs;

import java.util.function.Function;

public interface ConditionElement<T extends ConditionElement<?>> {

    Codec<ConditionElement<?>> CODEC = ExtraCodecs.lazyInitializedCodec(
            () -> ConditionElements.CONDITION_SERIALIZER_REGISTRY.byNameCodec().dispatch("condition", ConditionElement::codec, Function.identity()));


    boolean isMet();

    Codec<T> codec();

}
