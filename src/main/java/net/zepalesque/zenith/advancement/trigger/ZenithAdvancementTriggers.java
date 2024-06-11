package net.zepalesque.zenith.advancement.trigger;

import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.zenith.Zenith;

public class ZenithAdvancementTriggers {
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(Registries.TRIGGER_TYPE, Zenith.MODID);

    public static final DeferredHolder<CriterionTrigger<?>, StackingRecipeTrigger> STACKING_RECIPE = TRIGGERS.register("stacking_recipe", StackingRecipeTrigger::new);
}
