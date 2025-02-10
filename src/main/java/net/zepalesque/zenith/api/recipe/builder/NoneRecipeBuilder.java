package net.zepalesque.zenith.api.recipe.builder;

import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.zepalesque.zenith.api.recipe.recipes.NoneRecipe;
import org.jetbrains.annotations.Nullable;

public class NoneRecipeBuilder implements RecipeBuilder {

    public NoneRecipeBuilder of() {
        return new NoneRecipeBuilder();
    }

    protected NoneRecipeBuilder() { }

    @Override
    public RecipeBuilder unlockedBy(String s, Criterion<?> criterion) {
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String s) {
        return this;
    }

    @Override
    public Item getResult() {
        return Items.AIR;
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation id) {
        output.accept(id, new NoneRecipe(), null);
    }
}
