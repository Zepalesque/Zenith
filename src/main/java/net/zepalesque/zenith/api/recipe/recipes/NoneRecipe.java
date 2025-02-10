package net.zepalesque.zenith.api.recipe.recipes;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.zepalesque.zenith.core.recipe.input.EmptyRecipeInput;
import net.zepalesque.zenith.core.registry.ZenithRecipes;

public class NoneRecipe implements Recipe<EmptyRecipeInput> {
    @Override
    public boolean matches(EmptyRecipeInput container, Level level) {
        return false;
    }

    @Override
    public ItemStack assemble(EmptyRecipeInput container, HolderLookup.Provider registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ZenithRecipes.Serializers.NONE.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ZenithRecipes.NONE.get();
    }
}
