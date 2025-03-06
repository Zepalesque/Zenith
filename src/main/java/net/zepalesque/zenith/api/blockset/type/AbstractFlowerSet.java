package net.zepalesque.zenith.api.blockset.type;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.zenith.api.blockset.BlockSet;
import net.zepalesque.zenith.api.blockset.CraftingMatrix;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@SuppressWarnings("unused")
public abstract class AbstractFlowerSet implements BlockSet {

    protected abstract <T extends Block> DeferredBlock<T> flower(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, Supplier<T> constructor);
    public abstract DeferredBlock<?> flower();

    protected abstract DeferredBlock<?> pot(DeferredRegister.Blocks registry, String id);
    public abstract DeferredBlock<?> pot();

    public abstract AbstractFlowerSet craftsInto(ItemLike block, CraftingMatrix shape, RecipeCategory category);

    public abstract AbstractFlowerSet craftsIntoShapeless(int ingredientCount, ItemLike result, int resultCount, RecipeCategory category);

    public abstract AbstractFlowerSet withFlowerTag(TagKey<Block> tag);

    public abstract AbstractFlowerSet withPotTag(TagKey<Block> tag);

    public abstract AbstractFlowerSet withItemTag(TagKey<Item> tag);

    public abstract AbstractFlowerSet tabAfter(Supplier<CreativeModeTab> tab, ItemLike placeAfter, TabAdditionPhase phase);

    public abstract AbstractFlowerSet tabBefore(Supplier<CreativeModeTab> tab, ItemLike placeBefore, TabAdditionPhase phase);

    public abstract AbstractFlowerSet tabAppend(Supplier<CreativeModeTab> tab, TabAdditionPhase phase);

    public abstract AbstractFlowerSet compost(float amount);

    public abstract AbstractFlowerSet flammable(int encouragement, int flammability);

    public abstract AbstractFlowerSet inflammable();

    public abstract AbstractFlowerSet withPotProperties(UnaryOperator<Properties> properties);

    public abstract AbstractFlowerSet withPottedPrefix();
}
