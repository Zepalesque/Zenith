package net.zepalesque.zenith.api.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.providers.score.ScoreboardNameProviders;
import net.zepalesque.zenith.Zenith;

import java.util.Map;
import java.util.function.Function;

/**
 * A unified condition that can be used in a variety of datapack registries, to enable or disable certain things based on different conditions.
 * @param <T> The condition's class. For instance, an ExampleCondition should extend {@code Condition<ExampleCondition>}
 */
public interface Condition<T extends Condition<T>> {

    Codec<Condition<?>> ELEMENT_CODEC = Codec.lazyInitialized(
            () -> ConditionElements.ELEMENT_REGISTRY.byNameCodec().dispatch("element", Condition::codec, Function.identity()));
    Codec<Holder<Condition<?>>> CODEC = RegistryFileCodec.create(Zenith.Keys.CONDITION, ELEMENT_CODEC);

    boolean test();

    MapCodec<T> codec();

}
