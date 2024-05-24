package net.zepalesque.zenith.data.generator;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.zepalesque.zenith.Zenith;
import net.zepalesque.zenith.data.resource.Conditions;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public class ZenithRegistrySets extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Zenith.Keys.CONDITION, Conditions::bootstrap);


    public ZenithRegistrySets(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Collections.singleton(Zenith.MODID));
    }
}