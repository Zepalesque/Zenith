package net.zepalesque.zenith.loot.condition;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.zenith.Zenith;

public class ZenithLootConditions {

    public static final DeferredRegister<LootItemConditionType> LOOT_CONDITIONS = DeferredRegister.create(BuiltInRegistries.LOOT_CONDITION_TYPE, Zenith.MODID);

    public static final DeferredHolder<LootItemConditionType, LootItemConditionType> LOOT_MODULE =
            LOOT_CONDITIONS.register("when", () -> new LootItemConditionType(ConditionLootModule.CODEC));
}
