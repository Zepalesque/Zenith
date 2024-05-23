package net.zepalesque.zenith.recipe.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.zepalesque.zenith.api.condition.Condition;

import java.util.Objects;
import java.util.Optional;

public  class ConditionRecipeModule implements ICondition {

    public static Codec<ConditionRecipeModule> CODEC = RecordCodecBuilder.create(
            builder -> builder
                    .group(Condition.CODEC.fieldOf("condition").forGetter(module -> module.condition))
                    .apply(builder, ConditionRecipeModule::new));

    public final Holder<Condition<?>> condition;

    public ConditionRecipeModule(Holder<Condition<?>> pCondition)
    {
        this.condition = pCondition;
    }

    @Override
    public boolean test(IContext context) {
        Optional<Condition<?>> optional = this.condition.unwrap().right();
        return optional.isEmpty() || optional.get().test();
    }

    @Override
    public Codec<? extends ICondition> codec() {
        return CODEC;
    }

    @Override
    public String toString() {
        return "data_condition(" + Objects.requireNonNullElse(this.condition, "null") +  ")";
    }
}
