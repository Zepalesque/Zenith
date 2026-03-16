package net.zepalesque.zenith.api.blockset.type;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.zenith.api.blockset.BlockSet;

import java.util.function.Supplier;

public abstract class AbstractLeafSet<Self extends AbstractLeafSet<Self>> implements BlockSet {
	
	protected abstract <T extends Block> DeferredBlock<T> leaves(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, Supplier<T> constructor);
	public abstract DeferredBlock<?> leaves();
	
	protected abstract <T extends Block> DeferredBlock<T> sapling(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, Supplier<T> constructor);
	public abstract DeferredBlock<?> sapling();
	
	protected abstract DeferredBlock<?> pot(DeferredRegister.Blocks registry, String id);
	public abstract DeferredBlock<?> pot();
	
	public abstract Self withSaplingItemTag(TagKey<Item> tag);
	public abstract Self withLeafItemTag(TagKey<Item> tag);
	
	public abstract Self withSaplingTag(TagKey<Block> tag);
	public abstract Self withPotTag(TagKey<Block> tag);
	public abstract Self withLeafTag(TagKey<Block> tag);
	
	public abstract Self leafTabAfter(Supplier<CreativeModeTab> tab, ItemLike placeAfter, TabAdditionPhase phase);
	public abstract Self leafTabBefore(Supplier<CreativeModeTab> tab, ItemLike placeBefore, TabAdditionPhase phase);
	public abstract Self leafTabAppend(Supplier<CreativeModeTab> tab, TabAdditionPhase phase);
	
	public abstract Self saplingTabAfter(Supplier<CreativeModeTab> tab, ItemLike placeAfter, TabAdditionPhase phase);
	public abstract Self saplingTabBefore(Supplier<CreativeModeTab> tab, ItemLike placeBefore, TabAdditionPhase phase);
	public abstract Self saplingTabAppend(Supplier<CreativeModeTab> tab, TabAdditionPhase phase);
	
	public abstract Self leafCompost(float amount);
	public abstract Self saplingCompost(float amount);
	
	public abstract Self leafFlammable(int encouragement, int flammability);
	public abstract Self saplingFlammable(int encouragement, int flammability);
	
	public abstract TreeGrower grower();
}
