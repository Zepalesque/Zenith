package net.zepalesque.zenith.mixin.mixins.common.accessor;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FireBlock.class)
public interface FireAccessor {
    @Invoker
    void callSetFlammable(Block block, int encouragement, int flammability);
}