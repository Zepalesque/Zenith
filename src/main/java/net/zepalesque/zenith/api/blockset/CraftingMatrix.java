package net.zepalesque.zenith.api.blockset;

import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.level.ItemLike;
import net.zepalesque.zenith.api.blockset.type.AbstractStoneSet;
import net.zepalesque.zenith.api.blockset.type.AbstractFlowerSet;

import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

// TODO: Replace with alternative system? Maybe with a Consumer<RecipeOutput>?
public class CraftingMatrix {

    protected final BiFunction<ShapedRecipeBuilder, ItemLike, ShapedRecipeBuilder> operation;
    protected final int resultCount;

    /**
     * A matrix for {@link AbstractStoneSet} and {@link AbstractFlowerSet} crafting.
     * @param resultCount The number of items the recipe should give.
     * @param baseIngredient The character to represent the base block ingredient.
     * @param pattern The pattern of the recipe, should use the {@code baseIngredient} parameter character only.
     */
    public CraftingMatrix(int resultCount, char baseIngredient, String... pattern) {
        this(resultCount, (builder, item) -> {
            builder.define(baseIngredient, item);
            if (pattern.length > 3) {
                throw new UnsupportedOperationException("Pattern cannot have more than three rows");
            }
            for (String s : pattern) {
                builder.pattern(s);
            }
            return builder;
        });
    }

    /**
     * Direct constructor to perform a unary operation on a {@link ShapedRecipeBuilder}.
     * @param operation the {@link UnaryOperator} that should be performed. Can be used for more complex crafting behavior
     */
    public CraftingMatrix(int resultCount, BiFunction<ShapedRecipeBuilder, ItemLike, ShapedRecipeBuilder> operation) {
        this.operation = operation;
        this.resultCount = resultCount;
    }

    public ShapedRecipeBuilder apply(ShapedRecipeBuilder builder, ItemLike item) {
        return this.operation.apply(builder, item);
    }

    public int count() {
        return this.resultCount;
    }
}
