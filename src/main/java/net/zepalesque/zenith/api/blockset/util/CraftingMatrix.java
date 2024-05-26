package net.zepalesque.zenith.api.blockset.util;

import net.minecraft.data.recipes.ShapedRecipeBuilder;

import java.util.function.UnaryOperator;

public class CraftingMatrix {

    protected final UnaryOperator<ShapedRecipeBuilder> operation;
    protected final int resultCount;

    /**
     * A matrix for {@link net.zepalesque.zenith.api.blockset.AbstractStoneSet} crafting.
     * @param pattern The pattern of the recipe, should use hashtag/number symbols (#) only.
     */
    public CraftingMatrix(int resultCount, String... pattern) {
        this(resultCount, builder -> {
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
    public CraftingMatrix(int resultCount, UnaryOperator<ShapedRecipeBuilder> operation) {
        this.operation = operation;
        this.resultCount = resultCount;
    }

    public ShapedRecipeBuilder apply(ShapedRecipeBuilder builder) {
        return this.operation.apply(builder);
    }

    public int count() {
        return this.resultCount;
    }
}
