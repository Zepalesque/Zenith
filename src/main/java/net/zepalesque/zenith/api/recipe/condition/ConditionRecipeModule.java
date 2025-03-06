package net.zepalesque.zenith.api.recipe.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.zepalesque.zenith.api.condition.Condition;

import java.util.Objects;
import java.util.Optional;

public  class ConditionRecipeModule implements ICondition {

    public static final MapCodec<ConditionRecipeModule> CODEC = RecordCodecBuilder.mapCodec(
        builder -> builder
            .group(Condition.CODEC.fieldOf("condition").forGetter(instance -> instance.condition))
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
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }

    @Override
    public String toString() {
        return "data_condition(" + this.condition +  ")";
    }
}
