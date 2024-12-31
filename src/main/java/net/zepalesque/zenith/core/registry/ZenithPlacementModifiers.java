package net.zepalesque.zenith.core.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.zenith.core.Zenith;
import net.zepalesque.zenith.api.world.feature.placement.ConditionPlacementModule;

public class ZenithPlacementModifiers {

    public static final DeferredRegister<PlacementModifierType<?>> FILTERS = DeferredRegister.create(BuiltInRegistries.PLACEMENT_MODIFIER_TYPE, Zenith.MODID);

    public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<ConditionPlacementModule>> PLACEMENT_MODULE =
            FILTERS.register("when", () -> () -> ConditionPlacementModule.CODEC);
}
