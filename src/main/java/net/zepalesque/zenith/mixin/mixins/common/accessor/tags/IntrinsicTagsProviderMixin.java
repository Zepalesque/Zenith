package net.zepalesque.zenith.mixin.mixins.common.accessor.tags;

import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(IntrinsicHolderTagsProvider.class)
public interface IntrinsicTagsProviderMixin<T> {

    @Invoker
    IntrinsicHolderTagsProvider.IntrinsicTagAppender<T> callTag(TagKey<T> tag);

}
