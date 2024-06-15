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

    // For some reason or another, loot conditions don't like registry codecs, so just use the inline codec instead
    public static Codec<ConditionLootModule> CODEC = RecordCodecBuilder.create(
            builder -> builder
                    .group(Condition.ELEMENT_CODEC.fieldOf("inline_condition").forGetter(module -> module.condition))
                    .apply(builder, ConditionLootModule::new));

    private final Condition<?> condition;

    public ConditionLootModule(Condition<?> condition) {
        this.condition = condition;
    }

    public LootItemConditionType getType() {
        return ZenithLootConditions.LOOT_MODULE.get();
    }

    public boolean test(LootContext lootContext) {
        return this.condition.test();
    }

}
