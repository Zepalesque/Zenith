package net.zepalesque.zenith.world.placement;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.zenith.Zenith;

public class ZenithPlacementModifiers {

    public static final DeferredRegister<PlacementModifierType<?>> FILTERS = DeferredRegister.create(BuiltInRegistries.PLACEMENT_MODIFIER_TYPE, Zenith.MODID);

    public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<ConditionPlacementModule>> PLACEMENT_MODULE =
            FILTERS.register("when", () -> () -> ConditionPlacementModule.CODEC);
}
