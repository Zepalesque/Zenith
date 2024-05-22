package net.zepalesque.zenith.recipe.builder;

import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.zepalesque.zenith.recipe.recipes.AbstractStackingRecipe;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

public class StackingRecipeBuilder implements RecipeBuilder {
    private final ItemStack result;
    private final Ingredient ingredient;
    private Optional<CompoundTag> additionalData = Optional.empty();
    private final AbstractStackingRecipe.Factory<?> factory;

    public StackingRecipeBuilder(ItemStack result, Ingredient ingredient, AbstractStackingRecipe.Factory<?> factory) {
        this.result = result;
        this.ingredient = ingredient;
        this.factory = factory;

    }

    public static StackingRecipeBuilder recipe(Ingredient ingredient, ItemStack result, AbstractStackingRecipe.Factory<?> factory) {
        return new StackingRecipeBuilder(result, ingredient, factory);
    }

    @Override
    @NotNull
    public RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    public RecipeBuilder withExtra(CompoundTag data) {
        this.additionalData = Optional.of(data);
        return this;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public ItemStack getResultStack() {
        return this.result;
    }

    @Override
    @NotNull
    public Item getResult() {
        return Items.AIR;
    }

    @Override
    @NotNull
    @ParametersAreNonnullByDefault
    public RecipeBuilder unlockedBy(String criterionName, Criterion<?> criterionTrigger) {
        return this;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void save(RecipeOutput output, ResourceLocation id) {
        AbstractStackingRecipe recipe = this.factory.create(this.getIngredient(), this.getResultStack(), this.additionalData);
        output.accept(id, recipe, null);
    }
}
