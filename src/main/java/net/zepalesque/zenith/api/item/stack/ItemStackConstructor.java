package net.zepalesque.zenith.api.item.stack;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

/**
 * Stores information to construct an {@link ItemStack}.
 * @param item The {@link Item} for the stack.
 * @param components The {@link DataComponentPatch} for the stack.
 */
public record ItemStackConstructor(Holder<Item> item, Optional<DataComponentPatch> components) {
    public static final Codec<ItemStackConstructor> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            BuiltInRegistries.ITEM.holderByNameCodec().fieldOf("item").forGetter(ItemStackConstructor::item),
            DataComponentPatch.CODEC.optionalFieldOf("component_patch").forGetter(ItemStackConstructor::components)
    ).apply(builder, ItemStackConstructor::new));

    /**
     * Generate an {@link ItemStack} for this constructor.
     * @return The constructed {@link ItemStack}.
     */
    public ItemStack createStack(int count) {
        Preconditions.checkState(this.item().isBound(), "Tried to create stack for unbound holder!");
        return this.components().isPresent() ? new ItemStack(this.item(), count, this.components().get()) : new ItemStack(this.item(), 1);
    }
}
