package net.zepalesque.zenith.core.registry;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;
import net.zepalesque.zenith.core.Zenith;
import net.zepalesque.zenith.api.condition.Condition;
import net.zepalesque.zenith.api.condition.type.ConfigCondition;
import net.zepalesque.zenith.api.condition.type.DevEnvironmentCondition;
import net.zepalesque.zenith.api.condition.type.LogicConditions;
import net.zepalesque.zenith.api.condition.type.ModLoadedCondition;

@SuppressWarnings("unused")
public class ConditionElements {

    public static final DeferredRegister<MapCodec<? extends Condition<?>>> ELEMENTS = DeferredRegister.create(Zenith.Keys.CONDITION_ELEMENT, Zenith.MODID);
    public static final Registry<MapCodec<? extends Condition<?>>> ELEMENT_REGISTRY = new RegistryBuilder<>(Zenith.Keys.CONDITION_ELEMENT).sync(true).create();

    // Mod-Related Conditions
    public static final DeferredHolder<MapCodec<? extends Condition<?>>, MapCodec<ModLoadedCondition>> MOD_LOADED = ELEMENTS.register("mod_loaded", () -> ModLoadedCondition.CODEC);
    public static final DeferredHolder<MapCodec<? extends Condition<?>>, MapCodec<ConfigCondition>> REDUX_CONFIG = ELEMENTS.register("config_enabled", () -> ConfigCondition.CODEC);
    public static final DeferredHolder<MapCodec<? extends Condition<?>>, MapCodec<DevEnvironmentCondition>> DEV_ENVIRONMENT = ELEMENTS.register("dev_environment", () -> DevEnvironmentCondition.CODEC);

    // Logic Conditions TODO: More gates? Logic table system? arbitrary inputs
    public static final DeferredHolder<MapCodec<? extends Condition<?>>, MapCodec<LogicConditions.And<?, ?>>> AND = ELEMENTS.register("and", () -> LogicConditions.And.CODEC);
    public static final DeferredHolder<MapCodec<? extends Condition<?>>, MapCodec<LogicConditions.Not<?>>> NOT = ELEMENTS.register("not", () -> LogicConditions.Not.CODEC);
    public static final DeferredHolder<MapCodec<? extends Condition<?>>, MapCodec<LogicConditions.Or<?, ?>>> OR = ELEMENTS.register("or", () -> LogicConditions.Or.CODEC);

}
