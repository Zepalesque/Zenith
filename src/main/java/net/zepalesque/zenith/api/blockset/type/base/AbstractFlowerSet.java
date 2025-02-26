package net.zepalesque.zenith.api.blockset.type.base;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.zenith.api.blockset.core.AbstractSimpleBlockSet;
import net.zepalesque.zenith.api.blockset.core.CraftingMatrix;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public abstract class AbstractFlowerSet<S extends AbstractFlowerSet<S>> extends AbstractSimpleBlockSet<S> {

    protected abstract <T extends Block> DeferredBlock<T> flower(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, Supplier<T> constructor);
    public abstract DeferredBlock<?> flower();

    protected abstract DeferredBlock<?> pot(DeferredRegister.Blocks registry, String id);
    public abstract DeferredBlock<?> pot();

    public abstract S craftsInto(Supplier<? extends ItemLike> block, CraftingMatrix shape, RecipeCategory category);

    public abstract S craftsIntoShapeless(int ingredientCount, Supplier<? extends ItemLike> result, int resultCount, RecipeCategory category);

    public abstract S flammable(int encouragement, int flammability);

    public abstract S potProperties(UnaryOperator<Properties> properties);

    public abstract S potTexPrefix();
}
