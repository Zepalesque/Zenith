package net.zepalesque.zenith.recipe.condition;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.zepalesque.zenith.Zenith;

public class ZenithRecipeConditions {

    public static final DeferredRegister<Codec<? extends ICondition>> CODECS = DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, Zenith.MODID);

    public static final DeferredHolder<Codec<? extends ICondition>, Codec<ConditionRecipeModule>> RECIPE_MODULE =
            CODECS.register("when", () -> ConditionRecipeModule.CODEC);
}
