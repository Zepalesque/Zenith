package net.zepalesque.zenith.api.itemstack;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public record ItemStackConstructor(Holder<Item> item, Optional<DataComponentPatch> tag) {
    public static final Codec<ItemStackConstructor> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            BuiltInRegistries.ITEM.holderByNameCodec().fieldOf("item").forGetter(ItemStackConstructor::item),
            DataComponentPatch.CODEC.optionalFieldOf("component_patch").forGetter(ItemStackConstructor::tag)
    ).apply(builder, ItemStackConstructor::new));

    public ItemStack createStack() {
        Preconditions.checkState(this.item().isBound(), "Tried to create stack for unbound holder!");
        return this.tag().isPresent() ? new ItemStack(this.item(), 1, this.tag().get()) : new ItemStack(this.item(), 1);
    }
}
