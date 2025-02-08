package net.zepalesque.zenith.core.block.type;


import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.zenith.core.block.ZenithBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

/**
 * Meant to represent a block to not be replaced for certain Zenith features. Main usage is to skip lake floor replacement in {@link net.zepalesque.zenith.api.world.feature.gen.RuleBasedLakeFeature}.
 */
public final class VoidBlock extends Block {
    public VoidBlock() {
        super(Properties.of().air());
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return null;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return false;
    }

    public static <L extends LevelSimulatedReader> boolean ifNotVoid(L level, BlockPos pos, BiConsumer<L, BlockPos> onSuccess) {
        if (level.isStateAtPosition(pos, state -> !state.is(ZenithBlocks.VOID))) {
            onSuccess.accept(level, pos);
            return true;
        }
        return false;
    }
}
