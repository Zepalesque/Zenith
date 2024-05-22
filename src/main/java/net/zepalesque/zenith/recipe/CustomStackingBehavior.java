package net.zepalesque.zenith.recipe;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nullable;
import java.util.Optional;

public interface CustomStackingBehavior {

    @Nullable
    ItemStack transformStack(Ingredient ingredient, ItemStack original, RecipeType<?> type, Optional<CompoundTag> additionalData);
}