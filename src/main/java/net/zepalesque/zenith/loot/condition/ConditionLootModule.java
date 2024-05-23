package net.zepalesque.zenith.loot.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.zepalesque.zenith.api.condition.Condition;

import java.util.Optional;

public class ConditionLootModule implements LootItemCondition {

    public static Codec<ConditionLootModule> CODEC = RecordCodecBuilder.create(
            builder -> builder
                    .group(Condition.CODEC.fieldOf("condition").forGetter(module -> module.condition))
                    .apply(builder, ConditionLootModule::new));

    private final Holder<Condition<?>> condition;

    public ConditionLootModule(Holder<Condition<?>> condition)
    {
        this.condition = condition;
    }

    public LootItemConditionType getType() {
        return ZenithLootConditions.LOOT_MODULE.get();
    }

    public boolean test(LootContext lootContext) {
        Optional<Condition<?>> optional = this.condition.unwrap().right();
        return optional.isEmpty() || optional.get().test();
    }

}
