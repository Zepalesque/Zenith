package net.zepalesque.zenith.world.state;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.zenith.Zenith;

public class ZenithStateProviders {

    public static final DeferredRegister<BlockStateProviderType<?>> PROVIDERS = DeferredRegister.create(Registries.BLOCK_STATE_PROVIDER_TYPE, Zenith.MODID);

    public static DeferredHolder<BlockStateProviderType<?>, BlockStateProviderType<ConditionalStateProvider>> CONDITIONAL_STATE =
            PROVIDERS.register("conditional_state", () -> new BlockStateProviderType<>(ConditionalStateProvider.CODEC));
}