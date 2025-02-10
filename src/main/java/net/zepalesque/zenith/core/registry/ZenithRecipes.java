package net.zepalesque.zenith.core.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.zenith.api.recipe.recipes.NoneRecipe;
import net.zepalesque.zenith.api.recipe.serializer.NoneRecipeSerializer;
import net.zepalesque.zenith.core.Zenith;

public class ZenithRecipes {
    public static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, Zenith.MODID);
    public static final DeferredHolder<RecipeType<?>, RecipeType<NoneRecipe>> NONE = TYPES.register("none", () -> RecipeType.simple(Zenith.loc("infusion")));

    public static class Serializers {
        public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, Zenith.MODID);
        public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<NoneRecipe>> NONE = SERIALIZERS.register("none", NoneRecipeSerializer::new);

    }
}
