package net.zepalesque.zenith.world.state;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.zepalesque.zenith.api.condition.Condition;

import java.util.Optional;

public class ConditionalStateProvider extends BlockStateProvider {
    private final BlockStateProvider base;
    private final Holder<Condition<?>> condition;
    private final BlockStateProvider alternative;

    public static final Codec<ConditionalStateProvider> CODEC = RecordCodecBuilder.create((condition) ->
            condition.group(
                    BlockStateProvider.CODEC.fieldOf("base").forGetter((alt) -> alt.base),
                    Condition.CODEC.fieldOf("condition").forGetter((alt) -> alt.condition),
                    BlockStateProvider.CODEC.fieldOf("alternative").forGetter((alt) -> alt.alternative))
                    .apply(condition, ConditionalStateProvider::new));

    public ConditionalStateProvider(BlockStateProvider base, Holder<Condition<?>> condition, BlockStateProvider alternative) {
        this.base = base;
        this.condition = condition;
        this.alternative = alternative;
    }

    @Override
    public BlockState getState(RandomSource random, BlockPos pos) {
        Optional<Condition<?>> optional = this.condition.unwrap().right();
        boolean test = optional.isEmpty() || optional.get().test();
        return (test ? this.base : this.alternative).getState(random, pos);
    }
    protected BlockStateProviderType<?> type() {
        return ZenithStateProviders.CONDITIONAL_STATE.get();
    }

}
