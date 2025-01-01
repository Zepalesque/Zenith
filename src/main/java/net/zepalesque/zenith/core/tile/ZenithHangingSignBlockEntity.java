package net.zepalesque.zenith.core.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.zenith.api.blockset.type.AbstractWoodSet;

import java.util.function.Supplier;

public class ZenithHangingSignBlockEntity extends SignBlockEntity {
    private static final int MAX_TEXT_LINE_WIDTH = 60;
    private static final int TEXT_LINE_HEIGHT = 9;

    public ZenithHangingSignBlockEntity(BlockPos pos, BlockState state, Supplier<BlockEntityType<? extends ZenithHangingSignBlockEntity>> type) {
        super(type.get(), pos, state);
    }

    public int getTextLineHeight() {
        return TEXT_LINE_HEIGHT;
    }

    public int getMaxTextLineWidth() {
        return MAX_TEXT_LINE_WIDTH;
    }

    public SoundEvent getSignInteractionFailedSoundEvent() {
        return SoundEvents.WAXED_HANGING_SIGN_INTERACT_FAIL;
    }
}
