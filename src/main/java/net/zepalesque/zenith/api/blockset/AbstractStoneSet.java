package net.zepalesque.zenith.api.blockset;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.zenith.api.blockset.util.CraftingMatrix;

import java.util.function.Supplier;

public abstract class AbstractStoneSet implements BlockSet {

    protected abstract DeferredBlock<?> block(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType, float breakTime, float blastResistance);
    public abstract DeferredBlock<?> block();

    protected abstract DeferredBlock<?> wall(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType, float breakTime, float blastResistance);
    public abstract DeferredBlock<?> wall();

    protected abstract DeferredBlock<?> stairs(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType, float breakTime, float blastResistance);
    public abstract DeferredBlock<?> stairs();

    protected abstract DeferredBlock<?> slab(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType, float breakTime, float blastResistance);
    public abstract DeferredBlock<?> slab();

    public abstract AbstractStoneSet craftsIntoSet(Supplier<AbstractStoneSet> set, CraftingMatrix shape);

    public abstract AbstractStoneSet craftsInto(Supplier<? extends ItemLike> block, CraftingMatrix shape);

    public abstract AbstractStoneSet stonecutIntoSet(Supplier<AbstractStoneSet> set);

    public abstract AbstractStoneSet stonecutInto(Supplier<? extends ItemLike> result, int count);

    public abstract AbstractStoneSet smeltsIntoSet(Supplier<AbstractStoneSet> set, float experience);

    public abstract AbstractStoneSet smeltsInto(Supplier<? extends ItemLike> result, float experience);

    public abstract AbstractStoneSet withTag(TagKey<Block> tag, boolean allBlocks);

    public abstract AbstractStoneSet withItemTag(TagKey<Item> tag, boolean allBlocks);

    public abstract AbstractStoneSet tabAfter(Supplier<CreativeModeTab> tab, Supplier<? extends ItemLike> placeAfter, boolean allBlocks, TabAdditionPhase phase);

    public abstract AbstractStoneSet tabBefore(Supplier<CreativeModeTab> tab, Supplier<? extends ItemLike> placeBefore, boolean allBlocks, TabAdditionPhase phase);

    public abstract AbstractStoneSet tabAppend(Supplier<CreativeModeTab> tab, boolean allBlocks, TabAdditionPhase phase);

    protected abstract String baseName(boolean isBaseBlock);
}
