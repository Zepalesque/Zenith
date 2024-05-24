package net.zepalesque.zenith.api.blockset.util;

import net.minecraft.data.recipes.ShapedRecipeBuilder;

import java.util.function.UnaryOperator;

public class CraftingMatrix {

    protected final UnaryOperator<ShapedRecipeBuilder> operation;

    public CraftingMatrix(String... pattern) {
        this(builder -> {
            if (pattern.length > 3) {
                throw new UnsupportedOperationException("Pattern cannot have more than three rows");
            }
            for (String s : pattern) {
                builder.pattern(s);
            }
            return builder;
        });
    }
    public CraftingMatrix(UnaryOperator<ShapedRecipeBuilder> operation) {
        this.operation = operation;
    }


}
