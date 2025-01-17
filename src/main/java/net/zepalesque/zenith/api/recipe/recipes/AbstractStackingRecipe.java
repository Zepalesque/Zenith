package net.zepalesque.zenith.api.recipe.recipes;


import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.zepalesque.zenith.api.item.stack.ItemStackConstructor;
import net.zepalesque.zenith.api.item.CustomStackingBehavior;
import net.zepalesque.zenith.core.recipe.recipes.StackingRecipe;

import javax.annotation.Nullable;
import java.util.Optional;

public abstract class AbstractStackingRecipe implements StackingRecipe {

    protected final RecipeType<? extends AbstractStackingRecipe> type;
    protected final Ingredient ingredient;
    protected final ItemStackConstructor result;
    protected final Optional<CompoundTag> additional;
    protected final Optional<Holder<SoundEvent>> sound;

    public AbstractStackingRecipe(RecipeType<? extends AbstractStackingRecipe> type, Ingredient ingredient, ItemStackConstructor result, Optional<CompoundTag> additional, Optional<Holder<SoundEvent>> sound) {
        this.type = type;
        this.ingredient = ingredient;
        this.result = result;
        this.additional = additional;
        this.sound = sound;
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

        ItemStack resultStack = this.getResult().createStack();

        if (!originalStack.isComponentsPatchEmpty()) {
            resultStack.applyComponents(originalStack.getComponentsPatch());
        }

        resultStack.setCount(originalStack.getCount());
        if (resultStack.getItem() instanceof CustomStackingBehavior custom) {
            resultStack = custom.transformStack(this.ingredient, resultStack, this.type, this.additional.orElse(null));
        }
        return resultStack;
    }

    @Override
    public RecipeType<? extends AbstractStackingRecipe> getType() {
        return this.type;
    }

    @Override
    public Ingredient getIngredient() {
        return this.ingredient;
    }

    @Override
    public ItemStackConstructor getResult() {
        return this.result;
    }

    public Optional<CompoundTag> getAdditionalData() {
        return additional;
    }

    public Optional<Holder<SoundEvent>> getSound() {
        return sound;
    }

    public interface Factory<T extends AbstractStackingRecipe> {
        T create(Ingredient ingredient, ItemStackConstructor result, Optional<CompoundTag> additional, Optional<Holder<SoundEvent>> sound);
    }
}
