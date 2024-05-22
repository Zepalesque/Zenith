package net.zepalesque.zenith.recipe.recipes;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.zepalesque.zenith.recipe.CustomStackingBehavior;

import javax.annotation.Nullable;

public abstract class AbstractStackingRecipe implements StackingRecipe {

    protected final RecipeType<?> type;
    protected final Ingredient ingredient;
    protected final ItemStack result;
    // TODO: figure out if a CompoundTag is the best solution for this or not
    protected final CompoundTag additional;

    public AbstractStackingRecipe(RecipeType<?> type, ResourceLocation id, Ingredient ingredient, ItemStack result, CompoundTag additional) {
        this.type = type;
        this.ingredient = ingredient;
        this.result = result;
        this.additional = additional;
    }

    public boolean matches(Level level, ItemStack item) {
        return this.getIngredient().test(item);
    }

    @Nullable
    @Override
    public ItemStack getResultStack(ItemStack originalStack) {
        if (!this.getIngredient().test(originalStack)) {
            return null;
        }
        ItemStack resultStack = this.getResult();
        if (resultStack.getItem() instanceof CustomStackingBehavior custom) {
            resultStack = custom.transformStack(this.ingredient, resultStack, this.type, this.additional);
        }
        resultStack.setTag(originalStack.getTag());
        resultStack.setCount(originalStack.getCount());
        return resultStack;
    }

    @Override
    public RecipeType<?> getType() {
        return this.type;
    }

    @Override
    public Ingredient getIngredient() {
        return this.ingredient;
    }

    @Override
    public ItemStack getResult() {
        return this.result;
    }

}
