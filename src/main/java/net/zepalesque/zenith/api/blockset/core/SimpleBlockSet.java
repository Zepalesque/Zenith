package net.zepalesque.zenith.api.blockset.core;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.function.Predicate;
import java.util.function.Supplier;

// TODO
public interface SimpleBlockSet<S extends SimpleBlockSet<S>> extends BlockSet {

    @SuppressWarnings("unchecked")
    default S self() {
        return (S) this;
    }

    ItemLike[] items();

    Supplier<Block[]> blocks();

    @SuppressWarnings("unchecked")
    S putBlockTags(Predicate<Block> predicate, TagKey<Block>... tag);

    @SuppressWarnings("unchecked")
    S putItemTags(Predicate<Item> predicate, TagKey<Item>... tag);

    S tabAfter(Supplier<CreativeModeTab> tab, ItemLike placeAfter, boolean allBlocks, TabAdditionPhase phase);

    S tabBefore(Supplier<CreativeModeTab> tab, ItemLike placeBefore, boolean allBlocks, TabAdditionPhase phase);

    S tabAppend(Supplier<CreativeModeTab> tab, boolean allBlocks, TabAdditionPhase phase);
}
