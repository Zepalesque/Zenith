package net.zepalesque.zenith.core.recipe.recipes;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.zepalesque.zenith.api.item.stack.ItemStackConstructor;
import net.zepalesque.zenith.core.recipe.input.EmptyRecipeInput;

/**
 * Overrides anything container-related or item-related because these in-world recipes have no container. Instead, custom behavior is implemented by recipes that extend this.
 */
public interface StackingRecipe extends Recipe<EmptyRecipeInput> {

    Ingredient getIngredient();

    ItemStackConstructor getResult();

    ItemStack getResultStack(ItemStack originalState);

    @Override
    default boolean matches(EmptyRecipeInput container, Level level) {
        return false;
    }

    @Override
    default ItemStack assemble(EmptyRecipeInput container, HolderLookup.Provider registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    default boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    default ItemStack getResultItem(HolderLookup.Provider registryAccess) {
        return ItemStack.EMPTY;
    }


    @Override
    default NonNullList<ItemStack> getRemainingItems(EmptyRecipeInput container) {
        return NonNullList.create();
    }

    @Override
    default boolean isSpecial() {
        return true;
    }
}

