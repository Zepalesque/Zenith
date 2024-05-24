package net.zepalesque.zenith.data.resource;

import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.zenith.Zenith;
import net.zepalesque.zenith.api.condition.Condition;
import net.zepalesque.zenith.api.condition.ConfigCondition;
import net.zepalesque.zenith.api.condition.LogicConditions;
import net.zepalesque.zenith.api.condition.ModLoadedCondition;
import net.zepalesque.zenith.config.ZConfig;

import javax.annotation.Nullable;

public class Conditions {

    public static final ResourceKey<Condition<?>> EXAMPLE_CONDITION = createKey("example_condition");

    private static ResourceKey<Condition<?>> createKey(String name) {
        return ResourceKey.create(Zenith.Keys.CONDITION, Zenith.loc(name));
    }

    public static void bootstrap(BootstapContext<Condition<?>> context) {
        context.register(EXAMPLE_CONDITION,
                new LogicConditions.And<>(
                        new ModLoadedCondition(Zenith.MODID),
                        new ConfigCondition("zenith", ZConfig.COMMON.placeholder)
                ));
    }

    @Nullable
    public static ResourceKey<Condition<?>> getResourceKey(RegistryAccess registryAccess, String location) {
        return getResourceKey(registryAccess, new ResourceLocation(location));
    }


    @Nullable
    public static ResourceKey<Condition<?>> getResourceKey(RegistryAccess registryAccess, ResourceLocation location) {
        Condition<?> condition = getCondition(registryAccess, location);
        if (condition != null) {
            return registryAccess.registryOrThrow(Zenith.Keys.CONDITION).getResourceKey(condition).orElse(null);
        } else {
            return null;
        }
    }

    @Nullable
    public static ResourceKey<Condition<?>> getResourceKey(RegistryAccess registryAccess, Condition<?> condition) {
        return registryAccess.registryOrThrow(Zenith.Keys.CONDITION).getResourceKey(condition).orElse(null);
    }

    @Nullable
    public static Condition<?> getCondition(RegistryAccess registryAccess, String location) {
        return getCondition(registryAccess, new ResourceLocation(location));
    }

    @Nullable
    public static Condition<?> getCondition(RegistryAccess registryAccess, ResourceLocation location) {
        return registryAccess.registryOrThrow(Zenith.Keys.CONDITION).get(location);
    }
}
