package net.zepalesque.zenith.api.blockset.type.base;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.zenith.api.blockset.core.AbstractSimpleBlockSet;
import net.zepalesque.zenith.api.blockset.core.CraftingMatrix;

import java.util.function.Supplier;

public abstract class AbstractStoneSet<S extends AbstractStoneSet<S>> extends AbstractSimpleBlockSet<S> {

    protected abstract DeferredBlock<?> block(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType, float breakTime, float blastResistance);
    public abstract DeferredBlock<?> block();

    protected abstract DeferredBlock<?> wall(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType, float breakTime, float blastResistance);
    public abstract DeferredBlock<?> wall();

    protected abstract DeferredBlock<?> stairs(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType, float breakTime, float blastResistance);
    public abstract DeferredBlock<?> stairs();

    protected abstract DeferredBlock<?> slab(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType, float breakTime, float blastResistance);
    public abstract DeferredBlock<?> slab();

    public abstract S craftsIntoSet(Supplier<AbstractStoneSet<?>> set, CraftingMatrix shape);

    public abstract S craftsInto(Supplier<? extends ItemLike> block, CraftingMatrix shape);

    public abstract S stonecutIntoSet(Supplier<AbstractStoneSet<?>> set);

    public abstract S stonecutInto(Supplier<? extends ItemLike> result, int count);

    public abstract S smeltsIntoSet(Supplier<AbstractStoneSet<?>> set, float experience);

    public abstract S smeltsInto(Supplier<? extends ItemLike> result, float experience);

    protected abstract String baseName(boolean isBaseBlock);
}
