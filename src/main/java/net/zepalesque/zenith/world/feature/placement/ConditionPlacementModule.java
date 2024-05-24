package net.zepalesque.zenith.world.feature.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.zepalesque.zenith.api.condition.Condition;

import javax.annotation.Nonnull;
import java.util.Optional;

public class ConditionPlacementModule extends PlacementFilter {

    public static Codec<ConditionPlacementModule> CODEC = RecordCodecBuilder.create(
            builder -> builder
                    .group(Condition.CODEC.fieldOf("condition").forGetter(module -> module.condition))
                    .apply(builder, ConditionPlacementModule::new));

    public final Holder<Condition<?>> condition;

    private ConditionPlacementModule(Holder<Condition<?>> pCondition) {
        this.condition = pCondition;
    }

    public static ConditionPlacementModule of(Holder<Condition<?>> condition) {
        return new ConditionPlacementModule(condition);
    }

    protected boolean shouldPlace(@Nonnull PlacementContext context, @Nonnull RandomSource random, @Nonnull BlockPos pos) {
        Optional<Condition<?>> optional = this.condition.unwrap().right();
        return optional.isEmpty() || optional.get().test();
    }

    @Nonnull
    public PlacementModifierType<?> type() {
        return ZenithPlacementModifiers.PLACEMENT_MODULE.get();
    }
}


