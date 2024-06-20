package net.zepalesque.zenith.api.blockset;

import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.zenith.api.blockset.util.CraftingMatrix;

import java.util.function.Supplier;

public abstract class AbstractFlowerSet implements BlockSet {

    protected abstract DeferredBlock<?> flower(DeferredRegister.Blocks registry, DeferredRegister.Items items, Supplier<MobEffect> effect, String id, OffsetType offset, SoundType sound);
    public abstract DeferredBlock<?> flower();

    protected abstract DeferredBlock<?> pot(DeferredRegister.Blocks registry, String id);
    public abstract DeferredBlock<?> pot();

    public abstract AbstractFlowerSet craftsInto(Supplier<? extends ItemLike> block, CraftingMatrix shape);

    public abstract AbstractFlowerSet craftsIntoShapeless(Supplier<? extends ItemLike> block, int resultCount);

    public abstract AbstractFlowerSet withTag(TagKey<Block> tag, boolean allBlocks);

    public abstract AbstractFlowerSet withItemTag(TagKey<Item> tag, boolean allBlocks);

    public abstract AbstractFlowerSet creativeTab(Supplier<CreativeModeTab> tab, Supplier<? extends ItemLike> placeAfter, boolean allBlocks);




}
