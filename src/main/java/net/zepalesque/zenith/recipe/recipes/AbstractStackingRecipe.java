package net.zepalesque.zenith.recipe.recipes;


import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.zepalesque.zenith.item.CustomStackingBehavior;

import javax.annotation.Nullable;
import java.util.Optional;

public abstract class AbstractStackingRecipe implements StackingRecipe {

    protected final RecipeType<?> type;
    protected final Ingredient ingredient;
    protected final ItemStack result;
    // TODO: figure out if a CompoundTag is the best solution for this or not
    protected final Optional<CompoundTag> additional;
    protected final Optional<Holder<SoundEvent>> sound;

    public AbstractStackingRecipe(RecipeType<?> type, Ingredient ingredient, ItemStack result, Optional<CompoundTag> additional, Optional<Holder<SoundEvent>> sound) {
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
        ItemStack resultStack = this.getResult();

        resultStack.setTag(originalStack.getTag());
        resultStack.setCount(originalStack.getCount());
        if (resultStack.getItem() instanceof CustomStackingBehavior custom) {
            resultStack = custom.transformStack(this.ingredient, resultStack, this.type, this.additional);
        }
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

    public Optional<CompoundTag> getAdditionalData() {
        return additional;
    }

    public Optional<Holder<SoundEvent>> getSound() {
        return sound;
    }

    public interface Factory<T extends AbstractStackingRecipe> {
        T create(Ingredient ingredient, ItemStack result, Optional<CompoundTag> additional);
    }

}
