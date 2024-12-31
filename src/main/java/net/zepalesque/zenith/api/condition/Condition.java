package net.zepalesque.zenith.api.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.zepalesque.zenith.core.Zenith;
import net.zepalesque.zenith.core.registry.ConditionElements;

import java.util.function.Function;

/**
 * A unified condition that can be used in a variety of datapack registries, to enable or disable certain things based on different conditions.
 * @param <T> <p>The type of the subclass condition.</p>
 *            <p>For instance, an {@code ExampleCondition} should extend {@code Condition<ExampleCondition>}</p>
 */
public interface Condition<T extends Condition<T>> {

    Codec<Condition<?>> ELEMENT_CODEC = Codec.lazyInitialized(
            () -> ConditionElements.ELEMENT_REGISTRY.byNameCodec().dispatch("element", Condition::codec, Function.identity()));
    Codec<Holder<Condition<?>>> CODEC = RegistryFileCodec.create(Zenith.Keys.CONDITION, ELEMENT_CODEC);

    boolean test();

    MapCodec<T> codec();

}
