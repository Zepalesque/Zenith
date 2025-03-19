package net.zepalesque.zenith.api.recipe.builder;

import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.zepalesque.zenith.api.recipe.recipes.NoneRecipe;
import org.jetbrains.annotations.Nullable;

public record NoneRecipeBuilder() implements RecipeBuilder {
    
    public static final NoneRecipeBuilder INSTANCE = create();
   
    public NoneRecipeBuilder {
        if (!allowCreation) throw new AssertionError("Use the INSTANCE field instead");
    }
    
    private static boolean allowCreation = false;
    private static NoneRecipeBuilder create() {
        allowCreation = true;
        NoneRecipeBuilder recipe = new NoneRecipeBuilder();
        allowCreation = false;
        return recipe;
    }
    
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
        output.accept(id, NoneRecipe.INSTANCE, null);
    }
}
