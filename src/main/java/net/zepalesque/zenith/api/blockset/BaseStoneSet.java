package net.zepalesque.zenith.api.blockset;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.zenith.api.blockset.util.CraftingMatrix;

import java.util.function.Supplier;

public abstract class BaseStoneSet implements BlockSet {

    protected abstract DeferredBlock<?> generateBaseBlock(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);

    protected abstract DeferredBlock<?> generateWallBlock(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);

    protected abstract DeferredBlock<?> generateStairsBlock(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);

    protected abstract DeferredBlock<?> generateSlabBlock(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType);

    protected abstract void craftsInto(BaseStoneSet set, CraftingMatrix shape);

    protected abstract void craftsInto(Supplier<Block> block, CraftingMatrix shape);

    protected abstract void stonecutInto(BaseStoneSet set);

    protected abstract void stonecutInto(Supplier<Block> block);

    protected abstract void smeltsInto(BaseStoneSet set, float experience);

    protected abstract void smeltsInto(Supplier<Block> block, float experience);

}
