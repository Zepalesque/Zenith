package net.zepalesque.zenith.api.condition;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;
import net.zepalesque.zenith.Zenith;

public class ConditionElements {

    public static final DeferredRegister<Codec<? extends ConditionElement<?>>> ELEMENTS = DeferredRegister.create(Zenith.Keys.CONDITION_ELEMENT, Zenith.MODID);
    public static final Registry<Codec<? extends ConditionElement<?>>> CONDITION_SERIALIZER_REGISTRY = new RegistryBuilder<>(Zenith.Keys.CONDITION_ELEMENT).sync(true).create();

    // Mod-Related Conditions
    public static final DeferredHolder<Codec<? extends ConditionElement<?>>, Codec<ModLoadedCondition>> MOD_LOADED = ELEMENTS.register("mod_loaded", () -> ModLoadedCondition.CODEC);
    public static final DeferredHolder<Codec<? extends ConditionElement<?>>, Codec<ConfigCondition>> REDUX_CONFIG = ELEMENTS.register("config_enabled", () -> ConfigCondition.CODEC);

    // Logic
    public static final DeferredHolder<Codec<? extends ConditionElement<?>>, Codec<LogicConditions.And<?, ?>>> AND = ELEMENTS.register("and", () -> LogicConditions.And.CODEC);
    public static final DeferredHolder<Codec<? extends ConditionElement<?>>, Codec<LogicConditions.Not<?>>> NOT = ELEMENTS.register("not", () -> LogicConditions.Not.CODEC);
    public static final DeferredHolder<Codec<? extends ConditionElement<?>>, Codec<LogicConditions.Or<?, ?>>> OR = ELEMENTS.register("or", () -> LogicConditions.Or.CODEC);

}
