package net.zepalesque.zenith.api.condition;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;
import net.zepalesque.zenith.Zenith;

public class ConditionElements {

    public static final DeferredRegister<Codec<? extends Condition<?>>> ELEMENTS = DeferredRegister.create(Zenith.Keys.CONDITION_ELEMENT, Zenith.MODID);
    public static final Registry<Codec<? extends Condition<?>>> ELEMENT_REGISTRY = new RegistryBuilder<>(Zenith.Keys.CONDITION_ELEMENT).sync(true).create();

    // Mod-Related Conditions
    public static final DeferredHolder<Codec<? extends Condition<?>>, Codec<ModLoadedCondition>> MOD_LOADED = ELEMENTS.register("mod_loaded", () -> ModLoadedCondition.CODEC);
    public static final DeferredHolder<Codec<? extends Condition<?>>, Codec<ConfigCondition>> REDUX_CONFIG = ELEMENTS.register("config_enabled", () -> ConfigCondition.CODEC);
    public static final DeferredHolder<Codec<? extends Condition<?>>, Codec<DevEnvironmentCondition>> DEV_ENVIRONMENT = ELEMENTS.register("dev_environment", () -> DevEnvironmentCondition.CODEC);

    // Logic
    public static final DeferredHolder<Codec<? extends Condition<?>>, Codec<LogicConditions.And<?, ?>>> AND = ELEMENTS.register("and", () -> LogicConditions.And.CODEC);
    public static final DeferredHolder<Codec<? extends Condition<?>>, Codec<LogicConditions.Not<?>>> NOT = ELEMENTS.register("not", () -> LogicConditions.Not.CODEC);
    public static final DeferredHolder<Codec<? extends Condition<?>>, Codec<LogicConditions.Or<?, ?>>> OR = ELEMENTS.register("or", () -> LogicConditions.Or.CODEC);

}
