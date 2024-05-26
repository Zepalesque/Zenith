package net.zepalesque.zenith.api.blockset;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.zenith.api.blockset.util.CraftingMatrix;

import java.util.function.Supplier;

public abstract class AbstractStoneSet implements BlockSet {

    protected abstract DeferredBlock<?> baseBlock(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType);
    public abstract DeferredBlock<?> baseBlock();

    protected abstract DeferredBlock<?> wallBlock(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType);
    public abstract DeferredBlock<?> wallBlock();

    protected abstract DeferredBlock<?> stairsBlock(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType);
    public abstract DeferredBlock<?> stairsBlock();

    protected abstract DeferredBlock<?> slabBlock(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType);
    public abstract DeferredBlock<?> slabBlock();

    protected abstract AbstractStoneSet craftsInto(AbstractStoneSet set, CraftingMatrix shape);

    protected abstract AbstractStoneSet craftsInto(Supplier<Block> block, CraftingMatrix shape);

    protected abstract AbstractStoneSet stonecutInto(AbstractStoneSet set);

    protected abstract AbstractStoneSet stonecutInto(Supplier<Block> block);

    protected abstract AbstractStoneSet smeltsInto(AbstractStoneSet set, float experience);

    protected abstract AbstractStoneSet smeltsInto(Supplier<Block> block, float experience);

    protected abstract String nameSuffix(boolean isBaseBlock);
}
