package net.zepalesque.zenith.api.recipe.condition;

import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.zepalesque.zenith.core.Zenith;

public class ZenithRecipeConditions {

    public static final DeferredRegister<MapCodec<? extends ICondition>> CODECS = DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, Zenith.MODID);

    public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<ConditionRecipeModule>> RECIPE_MODULE =
            CODECS.register("when", () -> ConditionRecipeModule.CODEC);
}
