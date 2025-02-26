package net.zepalesque.zenith.api.condition.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.zepalesque.zenith.api.condition.Condition;

// TODO: Truth table condition
public class LogicConditions {

    public record And<E extends Condition<?>, T extends Condition<?>>(E arg1, T arg2)
            implements Condition<And<?, ?>> {

        public static final MapCodec<And<?, ?>> CODEC = RecordCodecBuilder.mapCodec((condition) ->
                condition.group(Condition.ELEMENT_CODEC.fieldOf("arg1").forGetter(And::arg1),
                                Condition.ELEMENT_CODEC.fieldOf("arg2").forGetter(And::arg2))
                        .apply(condition, And::new));

        @Override
        public boolean test() {
            return this.arg1.test() && this.arg2.test();
        }

        @Override
        public MapCodec<And<?, ?>> codec() {
            return CODEC;
        }

    }

    public record Not<E extends Condition<?>>(E condition)
            implements Condition<Not<?>> {

        public static final MapCodec<Not<?>> CODEC = RecordCodecBuilder.mapCodec((condition) ->
                condition.group(Condition.ELEMENT_CODEC.fieldOf("inverted").forGetter(Not::condition))
                        .apply(condition, Not::new));

        @Override
        public boolean test() {
            return !this.condition.test();
        }

        @Override
        public MapCodec<Not<?>> codec() {
            return CODEC;
        }
    }

    public record Or<E extends Condition<?>, T extends Condition<?>>(E arg1, T arg2)
            implements Condition<Or<?, ?>> {

        public static final MapCodec<Or<?, ?>> CODEC = RecordCodecBuilder.mapCodec((condition) ->
                condition.group(Condition.ELEMENT_CODEC.fieldOf("arg1").forGetter((cond) -> cond.arg1),
                                Condition.ELEMENT_CODEC.fieldOf("arg2").forGetter((cond) -> cond.arg2))
                        .apply(condition, Or::new));

        @Override
        public boolean test() {
            return this.arg1.test() || this.arg2.test();
        }

        @Override
        public MapCodec<Or<?, ?>> codec() {
            return CODEC;
        }

    }
}
