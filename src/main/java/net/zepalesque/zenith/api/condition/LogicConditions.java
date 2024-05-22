package net.zepalesque.zenith.api.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class LogicConditions {

    public static class And<E extends ConditionElement<?>, T extends ConditionElement<?>> implements ConditionElement<And<?, ?>> {

        public static final Codec<And<?, ?>> CODEC = RecordCodecBuilder.create((condition) ->
                condition.group(ConditionElement.CODEC.fieldOf("arg1").forGetter((cond) -> cond.arg1),
                                ConditionElement.CODEC.fieldOf("arg2").forGetter((cond) -> cond.arg2))
                        .apply(condition, And::new));

        protected final E arg1;
        protected final T arg2;

        public And(E arg1, T arg2) {
            this.arg1 = arg1;
            this.arg2 = arg2;
        }

        @Override
        public boolean isMet() {
            return this.arg1.isMet() && this.arg2.isMet();
        }

        @Override
        public Codec<And<?, ?>> codec() {
            return CODEC;
        }

    }

    public static class Not<E extends ConditionElement<?>> implements ConditionElement<Not<?>> {

        public static final Codec<Not<?>> CODEC = RecordCodecBuilder.create((condition) ->
                condition.group(ConditionElement.CODEC.fieldOf("inverted").forGetter((cond) -> cond.condition))
                        .apply(condition, Not::new));

        protected final E condition;




        public Not(E condition) {
            this.condition = condition;
        }

        @Override
        public boolean isMet() {
            return !this.condition.isMet();
        }

        @Override
        public Codec<Not<?>> codec() {
            return CODEC;
        }
    }

    public static class Or<E extends ConditionElement<?>, T extends ConditionElement<?>> implements ConditionElement<Or<?, ?>> {

        public static final Codec<Or<?, ?>> CODEC = RecordCodecBuilder.create((condition) ->
                condition.group(ConditionElement.CODEC.fieldOf("arg1").forGetter((cond) -> cond.arg1),
                                ConditionElement.CODEC.fieldOf("arg2").forGetter((cond) -> cond.arg2))
                        .apply(condition, Or::new));

        protected final E arg1;
        protected final T arg2;




        public Or(E arg1, T arg2) {
            this.arg1 = arg1;
            this.arg2 = arg2;
        }

        @Override
        public boolean isMet() {
            return this.arg1.isMet() || this.arg2.isMet();
        }

        @Override
        public Codec<Or<?, ?>> codec() {
            return CODEC;
        }

    }
}
