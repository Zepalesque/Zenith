package net.zepalesque.zenith.mixin.mixins.common.accessor.tags;

import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemTagsProvider.class)
public interface ItemTagsProviderMixin extends IntrinsicTagsProviderMixin<Item> { }
