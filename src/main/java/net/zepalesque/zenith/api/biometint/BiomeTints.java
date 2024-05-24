package net.zepalesque.zenith.api.biometint;

import net.minecraft.core.Registry;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;
import net.zepalesque.zenith.Zenith;

public class BiomeTints {

    public static final DeferredRegister<BiomeTint> TINTS = DeferredRegister.create(Zenith.Keys.BIOME_TINT, Zenith.MODID);
    public static final Registry<BiomeTint> TINT_REGISTRY = new RegistryBuilder<>(Zenith.Keys.BIOME_TINT).sync(true).create();

    public static final DeferredHolder<BiomeTint, BiomeTint> EXAMPLE_TINT = TINTS.register("example_tint", () -> new BiomeTint(Zenith.loc("example_tint"), 0xFFFFFF));


}
