package net.zepalesque.zenith.api.loot.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.zepalesque.zenith.api.condition.Condition;
import net.zepalesque.zenith.core.registry.ZenithLootConditions;

public record ConditionLootModule(Condition<?> condition) implements LootItemCondition {

    // For some reason or another, loot conditions don't like registry codecs, so just use the inline codec instead
    public static final MapCodec<ConditionLootModule> CODEC = RecordCodecBuilder.mapCodec(
        builder -> builder
            .group(Condition.ELEMENT_CODEC.fieldOf("inline_condition").forGetter(ConditionLootModule::condition))
            .apply(builder, ConditionLootModule::new));

    public LootItemConditionType getType() {
        return ZenithLootConditions.LOOT_MODULE.get();
    }

    public boolean test(LootContext lootContext) {
        return this.condition().test();
    }

}
