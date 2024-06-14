package net.zepalesque.zenith.recipe.builder;

import net.minecraft.advancements.Criterion;
import net.minecraft.core.Holder;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.zepalesque.zenith.api.itemstack.ItemStackConstructor;
import net.zepalesque.zenith.recipe.recipes.AbstractStackingRecipe;

import javax.annotation.Nullable;
import java.util.Optional;

public class StackingRecipeBuilder implements RecipeBuilder {
    private final Ingredient ingredient;
    private final ItemStackConstructor result;
    private Optional<CompoundTag> extra = Optional.empty();
    private Optional<Holder<SoundEvent>> sound = Optional.empty();
    private final AbstractStackingRecipe.Factory<?> factory;

    public StackingRecipeBuilder(Ingredient ingredient, ItemStackConstructor result, AbstractStackingRecipe.Factory<?> factory) {
        this.ingredient = ingredient;
        this.result = result;
        this.factory = factory;

    }

    public static StackingRecipeBuilder recipe(Ingredient ingredient, ItemStackConstructor result, AbstractStackingRecipe.Factory<?> factory) {
        return new StackingRecipeBuilder(ingredient, result, factory);
    }

    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    public StackingRecipeBuilder withExtra(CompoundTag data) {
        this.extra = Optional.of(data);
        return this;
    }

    public StackingRecipeBuilder withSound(Holder<SoundEvent> sound) {
        this.sound = Optional.of(sound);
        return this;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public ItemStackConstructor getResultStack() {
        return this.result;
    }

    @Override
    public Item getResult() {
        return Items.AIR;
    }

    @Override
    public RecipeBuilder unlockedBy(String criterionName, Criterion<?> criterionTrigger) {
        return this;
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation id) {
        AbstractStackingRecipe recipe = this.factory.create(this.getIngredient(), this.getResultStack(), this.extra, this.sound);
        output.accept(id, recipe, null);
    }
}
