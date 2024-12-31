package net.zepalesque.zenith.api.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.zepalesque.zenith.api.recipe.recipes.AbstractStackingRecipe;
import net.zepalesque.zenith.core.recipe.recipes.StackingRecipe;

import javax.annotation.Nullable;

/**
 * An interface that allows items to utilize custom {@link StackingRecipe StackingRecipe} mechanics.
 */
public interface CustomStackingBehavior {

    /**
     * Performs additional modifications to an {@link ItemStack} result of a stacking recipe
     * @param ingredient The ingredient of the recipe.
     * @param result The initial result {@link ItemStack}.
     * @param type The relevant {@link RecipeType}.
     * @param additionalData Additional data stored in the recipe JSON.
     * @return The modified {@code original} {@link ItemStack}, if (and only if) any changes have been made.
     */
    @Nullable
    ItemStack transformStack(Ingredient ingredient, ItemStack result, RecipeType<? extends AbstractStackingRecipe> type, @Nullable CompoundTag additionalData);
}
