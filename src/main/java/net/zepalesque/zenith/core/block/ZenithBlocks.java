package net.zepalesque.zenith.core.block;

import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.zenith.core.Zenith;
import net.zepalesque.zenith.core.block.type.VoidBlock;

public class ZenithBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Zenith.MODID);

    public static DeferredBlock<VoidBlock> VOID = BLOCKS.register("void", VoidBlock::new);
}
