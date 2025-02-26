package net.zepalesque.zenith.mixin.mixins.common.accessor.tags;

import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockTagsProvider.class)
public interface BlockTagsProviderMixin extends IntrinsicTagsProviderMixin<Block> { }
