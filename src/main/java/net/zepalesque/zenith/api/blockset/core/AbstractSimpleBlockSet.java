package net.zepalesque.zenith.api.blockset.core;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.zepalesque.zenith.mixin.mixins.common.accessor.tags.BlockTagsProviderMixin;
import net.zepalesque.zenith.mixin.mixins.common.accessor.tags.ItemTagsProviderMixin;
import net.zepalesque.zenith.util.ArrayUtil;
import net.zepalesque.zenith.util.item.TabUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class AbstractSimpleBlockSet<S extends AbstractSimpleBlockSet<S>> extends AbstractBlockSet implements SimpleBlockSet<S> {

    protected final Table<Supplier<CreativeModeTab>, ItemLike, Pair<Boolean, TabAdditionPhase>> afterOrdering = HashBasedTable.create();
    protected final Table<Supplier<CreativeModeTab>, ItemLike, Pair<Boolean, TabAdditionPhase>> beforeOrdering = HashBasedTable.create();
    protected final Table<Supplier<CreativeModeTab>, TabAdditionPhase, Boolean> appended = HashBasedTable.create();
    protected final Map<Predicate<Block>, TagKey<Block>[]> blockTags = new HashMap<>();
    protected final Map<Predicate<Item>, TagKey<Item>[]> itemTags = new HashMap<>();

    protected final Set<BiConsumer<RecipeProvider, RecipeOutput>> extraCrafting = new HashSet<>();

    protected final Map<Predicate<Item>, Float> compost = new HashMap<>();

    @SafeVarargs
    @Override
    public final S putBlockTags(Predicate<Block> predicate, TagKey<Block>... tag) {
        this.blockTags.merge(predicate, tag, ArrayUtil::union);
        return self();
    }

    @SafeVarargs
    @Override
    public final S putItemTags(Predicate<Item> predicate, TagKey<Item>... tag) {
        this.itemTags.merge(predicate, tag, ArrayUtil::union);
        return self();
    }

    @Override
    public S tabAfter(Supplier<CreativeModeTab> tab, ItemLike placeAfter, boolean allBlocks, TabAdditionPhase phase) {
        this.afterOrdering.put(tab, placeAfter, Pair.of(allBlocks, phase));
        return self();
    }

    @Override
    public S tabBefore(Supplier<CreativeModeTab> tab, ItemLike placeBefore, boolean allBlocks, TabAdditionPhase phase) {
        this.beforeOrdering.put(tab, placeBefore, Pair.of(allBlocks, phase));
        return self();
    }

    @Override
    public S tabAppend(Supplier<CreativeModeTab> tab, boolean allBlocks, TabAdditionPhase phase) {
        this.appended.put(tab, phase, allBlocks);
        return self();
    }

    public S extraCrafting(BiConsumer<RecipeProvider, RecipeOutput> consumer) {
        this.extraCrafting.add(consumer);
        return self();
    }

    // Ignore the prev value, that is for sets that are to be placed together procedurally, but simple sets allow specific ordering setup
    @Override
    public Supplier<? extends ItemLike> addToCreativeTab(BuildCreativeModeTabContentsEvent event, Supplier<? extends ItemLike> prev, TabAdditionPhase phase) {
        for (Table.Cell<Supplier<CreativeModeTab>, ItemLike, Pair<Boolean, TabAdditionPhase>> triple : this.afterOrdering.cellSet()) {
            Supplier<CreativeModeTab> tabToAddTo = triple.getRowKey();
            if (TabUtil.isForTab(event, tabToAddTo)) {
                ItemLike addAfter = triple.getColumnKey();
                Pair<Boolean, TabAdditionPhase> pair = triple.getValue();
                if (pair.getSecond() == phase) {
                    ItemLike[] entries = this.items();
                    if (pair.getFirst()) TabUtil.putAfter(event, addAfter, entries[0], Arrays.copyOfRange(entries, 1, entries.length));
                    else TabUtil.putAfter(event, addAfter, entries[0]);
                }
            }
        }
        for (Table.Cell<Supplier<CreativeModeTab>, ItemLike, Pair<Boolean, TabAdditionPhase>> triple : this.beforeOrdering.cellSet()) {
            Supplier<CreativeModeTab> tabToAddTo = triple.getRowKey();
            if (TabUtil.isForTab(event, tabToAddTo)) {
                ItemLike addBefore = triple.getColumnKey();
                Pair<Boolean, TabAdditionPhase> pair = triple.getValue();
                if (pair.getSecond() == phase) {
                    ItemLike[] entries = this.items();
                    if (pair.getFirst()) TabUtil.putAfter(event, addBefore, entries[0], Arrays.copyOfRange(entries, 1, entries.length));
                    else TabUtil.putBefore(event, addBefore, entries[0]);
                }
            }
        }
        for (Table.Cell<Supplier<CreativeModeTab>, TabAdditionPhase, Boolean> triple : this.appended.cellSet()) {
            Supplier<CreativeModeTab> tabToAddTo = triple.getRowKey();
            if (TabUtil.isForTab(event, tabToAddTo)) {
                boolean addAll = triple.getValue();
                TabAdditionPhase current = triple.getColumnKey();
                if (current == phase) {
                    ItemLike[] entries = this.items();
                    if (addAll) TabUtil.put(event, entries[0], Arrays.copyOfRange(entries, 1, entries.length));
                    else TabUtil.put(event, entries[0]);
                }
            }
        }
        return null;
    }

    public S compost(Predicate<Item> predicate, float amount) {
        compost.put(predicate, amount);
        return self();
    }

    public abstract S flammable(int encouragement, int flammability);


    @Override
    public void recipeData(RecipeProvider data, RecipeOutput output) {
        this.extraCrafting.forEach(consumer -> consumer.accept(data, output));
    }

    @Override
    public void mapData(DataMapProvider data) {
        for (ItemLike i : this.items())
            for (var entry : this.compost.entrySet()) {
                if (entry.getKey().test(i.asItem()))
                    data.builder(NeoForgeDataMaps.COMPOSTABLES).add(i.asItem().builtInRegistryHolder(), new Compostable(entry.getValue()), false);
        }
    }

    @Override
    public void itemTagData(ItemTagsProvider data) {
        for (ItemLike i : this.items()) for (var entry : this.itemTags.entrySet())
            if (entry.getKey().test(i.asItem()))
                for (TagKey<Item> tag : entry.getValue()) ((ItemTagsProviderMixin) data).callTag(tag).add(i.asItem());
    }

    @Override
    public void blockTagData(BlockTagsProvider data) {
        for (Supplier<Block> b : this.blocks()) for (var entry : this.blockTags.entrySet())
            if (entry.getKey().test(b.get()))
                for (TagKey<Block> tag : entry.getValue()) ((BlockTagsProviderMixin) data).callTag(tag).add(b.get());
    }
}
