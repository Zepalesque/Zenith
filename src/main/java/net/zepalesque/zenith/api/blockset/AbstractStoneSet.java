package net.zepalesque.zenith.api.blockset;

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

    protected abstract AbstractStoneSet craftsInto(AbstractStoneSet set, CraftingMatrix shape);

    protected abstract AbstractStoneSet craftsInto(Supplier<Block> block, CraftingMatrix shape);

    protected abstract AbstractStoneSet stonecutInto(AbstractStoneSet set);

    protected abstract AbstractStoneSet stonecutInto(Supplier<Block> block, int count);

    protected abstract AbstractStoneSet smeltsInto(AbstractStoneSet set, float experience);

    protected abstract AbstractStoneSet smeltsInto(Supplier<Block> block, float experience);

    public abstract String baseName(boolean isBaseBlock);
}
