package net.zepalesque.zenith;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;
import net.zepalesque.zenith.advancement.trigger.ZenithAdvancementTriggers;
import net.zepalesque.zenith.api.biometint.BiomeTint;
import net.zepalesque.zenith.api.biometint.BiomeTints;
import net.zepalesque.zenith.api.condition.Condition;
import net.zepalesque.zenith.api.condition.ConditionElements;
import net.zepalesque.zenith.config.ZConfig;
import net.zepalesque.zenith.config.ZConfigHandler;
import net.zepalesque.zenith.data.generator.ZenithDataMapGen;
import net.zepalesque.zenith.data.generator.ZenithRegistrySets;
import net.zepalesque.zenith.loot.condition.ZenithLootConditions;
import net.zepalesque.zenith.network.packet.BiomeTintSyncPacket;
import net.zepalesque.zenith.recipe.condition.ZenithRecipeConditions;
import net.zepalesque.zenith.world.biome.modifier.ZenithBiomeModifiers;
import net.zepalesque.zenith.world.density.ZenithDensityFunctions;
import net.zepalesque.zenith.world.feature.gen.ZenithFeatures;
import net.zepalesque.zenith.world.feature.placement.ZenithPlacementModifiers;
import net.zepalesque.zenith.world.state.ZenithStateProviders;
import net.zepalesque.zenith.world.structure.modifier.ZenithStructureModifiers;
import net.zepalesque.zenith.world.tree.trunk.ZenithTrunkPlacers;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

// TODO: More documentation
@Mod(Zenith.MODID)
public class Zenith {
    public static final String MODID = "zenith";

    public static final Logger LOGGER = LogUtils.getLogger();

    public Zenith(IEventBus bus, Dist dist) {

        bus.addListener(this::commonSetup);
        bus.addListener(this::registerPackets);
        bus.addListener(this::registerDataMaps);
        bus.addListener(this::registerRegistries);
        bus.addListener(this::dataSetup);
        bus.addListener(DataPackRegistryEvent.NewRegistry.class, event -> event.dataPackRegistry(Keys.CONDITION, Condition.ELEMENT_CODEC, Condition.ELEMENT_CODEC));

        DeferredRegister<?>[] registers = {
                ConditionElements.ELEMENTS,
                BiomeTints.TINTS,
                ZenithRecipeConditions.CODECS,
                ZenithPlacementModifiers.FILTERS,
                ZenithLootConditions.LOOT_CONDITIONS,
                ZenithStateProviders.PROVIDERS,
                ZenithFeatures.FEATURES,
                ZenithBiomeModifiers.CODECS,
                ZenithStructureModifiers.CODECS,
                ZenithDensityFunctions.FUNCTIONS,
                ZenithAdvancementTriggers.TRIGGERS,
                ZenithTrunkPlacers.TRUNK_PLACERS
        };

        for (DeferredRegister<?> register : registers) {
            register.register(bus);
        }

        // Register example config serializer
        ZConfig.COMMON.registerSerializer();
        
        ZConfigHandler.setup(bus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    public void registerPackets(RegisterPayloadHandlerEvent event) {
        IPayloadRegistrar registrar = event.registrar(MODID).versioned("1.0.0").optional();
        registrar.play(BiomeTintSyncPacket.ID, BiomeTintSyncPacket::decode, payload -> payload.client(BiomeTintSyncPacket::handle));

    }

    private void dataSetup(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        PackOutput packOutput = generator.getPackOutput();


        generator.addProvider(event.includeServer(), new ZenithDataMapGen(packOutput, lookupProvider));

        ZenithRegistrySets registrySets = new ZenithRegistrySets(packOutput, lookupProvider);
//        CompletableFuture<HolderLookup.Provider> registryProvider = registrySets.getRegistryProvider();
        generator.addProvider(event.includeServer(), registrySets);
    }

    private void registerDataMaps(RegisterDataMapTypesEvent event) {
        BiomeTints.TINT_REGISTRY.forEach(tint -> tint.register(event));
    }

    private void registerRegistries(NewRegistryEvent event) {
        event.register(ConditionElements.ELEMENT_REGISTRY);
        event.register(BiomeTints.TINT_REGISTRY);
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }

    }

    public static ResourceLocation loc(String path) {
        return new ResourceLocation(MODID, path);
    }

    public static class Keys {

        public static final ResourceKey<Registry<Codec<? extends Condition<?>>>> CONDITION_ELEMENT = ResourceKey.createRegistryKey(Zenith.loc("condition_element"));
        public static final ResourceKey<Registry<Condition<?>>> CONDITION = ResourceKey.createRegistryKey(Zenith.loc("condition"));
        public static final ResourceKey<Registry<BiomeTint>> BIOME_TINT = ResourceKey.createRegistryKey(Zenith.loc("biome_tint"));
    }


}


