package net.zepalesque.zenith.api.blockset;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
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

    public abstract AbstractStoneSet craftsInto(AbstractStoneSet set, CraftingMatrix shape);

    public abstract AbstractStoneSet craftsInto(Supplier<Block> block, CraftingMatrix shape);

    public abstract AbstractStoneSet stonecutInto(AbstractStoneSet set);

    public abstract AbstractStoneSet stonecutInto(Supplier<Block> block, int count);

    public abstract AbstractStoneSet smeltsInto(AbstractStoneSet set, float experience);

    public abstract AbstractStoneSet smeltsInto(Supplier<Block> block, float experience);

    public abstract AbstractStoneSet withTag(TagKey<Block> tag, boolean allBlocks);

    public abstract AbstractStoneSet creativeTab(Supplier<CreativeModeTab> tab, Supplier<Block> placeAfter, boolean allBlocks);

    protected abstract String baseName(boolean isBaseBlock);
}
