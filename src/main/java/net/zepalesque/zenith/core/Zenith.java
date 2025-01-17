package net.zepalesque.zenith.core;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;
import net.zepalesque.zenith.api.extendablestate.ExtendableStateList;
import net.zepalesque.zenith.core.registry.StateLists;
import net.zepalesque.zenith.core.registry.ZenithAdvancementTriggers;
import net.zepalesque.zenith.api.biometint.BiomeTint;
import net.zepalesque.zenith.core.registry.BiomeTints;
import net.zepalesque.zenith.api.condition.Condition;
import net.zepalesque.zenith.core.registry.ConditionElements;
import net.zepalesque.zenith.core.config.ZConfig;
import net.zepalesque.zenith.core.config.ZConfigHandler;
import net.zepalesque.zenith.core.data.generator.ZenithDataMapGen;
import net.zepalesque.zenith.core.data.generator.ZenithRegistrySets;
import net.zepalesque.zenith.core.registry.ZenithBlockPredicates;
import net.zepalesque.zenith.core.registry.ZenithLootConditions;
import net.zepalesque.zenith.core.network.packet.BiomeTintSyncPacket;
import net.zepalesque.zenith.core.registry.ZenithRecipeConditions;
import net.zepalesque.zenith.core.registry.ZenithBiomeModifiers;
import net.zepalesque.zenith.core.registry.ZenithDensityFunctions;
import net.zepalesque.zenith.core.registry.ZenithFeatures;
import net.zepalesque.zenith.core.registry.ZenithPlacementModifiers;
import net.zepalesque.zenith.core.registry.ZenithStateProviders;
import net.zepalesque.zenith.core.registry.ZenithStructureModifiers;
import net.zepalesque.zenith.core.registry.ZenithTrunkPlacers;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;

// TODO: More documentation
@Mod(Zenith.MODID)
public class Zenith {
    public static final String MODID = "zenith";

    public static final Logger LOGGER = LogUtils.getLogger();

    public Zenith(ModContainer mod, IEventBus bus, Dist dist) {
        bus.addListener(this::commonSetup);
        bus.addListener(this::registerPackets);
        bus.addListener(this::registerDataMaps);
        bus.addListener(this::registerRegistries);
        bus.addListener(this::dataSetup);
        bus.addListener(this::datapackRegistries);

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
                ZenithTrunkPlacers.TRUNK_PLACERS,
                ZenithBlockPredicates.PREDICATES
        };

        for (DeferredRegister<?> register : registers) {
            register.register(bus);
        }

        // Register example config serializer
        ZConfig.COMMON.registerSerializer();
        
        ZConfigHandler.setup(mod, bus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    public void registerPackets(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(MODID).versioned("1.0.0").optional();
        registrar.playToClient(BiomeTintSyncPacket.TYPE, BiomeTintSyncPacket.STREAM_CODEC, BiomeTintSyncPacket::execute);
    }

    private void datapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(Keys.CONDITION, Condition.ELEMENT_CODEC, Condition.ELEMENT_CODEC);
        event.dataPackRegistry(Keys.EXTENDABLE_STATE_LIST_ENTRY, ExtendableStateList.Entry.CODEC);
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
        event.register(StateLists.STATE_LIST_REGISTRY);
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }

    }

    public static ResourceLocation loc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public static class Keys {

        public static final ResourceKey<Registry<MapCodec<? extends Condition<?>>>> CONDITION_ELEMENT = ResourceKey.createRegistryKey(Zenith.loc("condition_element"));
        public static final ResourceKey<Registry<Condition<?>>> CONDITION = ResourceKey.createRegistryKey(Zenith.loc("condition"));
        public static final ResourceKey<Registry<BiomeTint>> BIOME_TINT = ResourceKey.createRegistryKey(Zenith.loc("biome_tint"));
        public static final ResourceKey<Registry<ExtendableStateList>> EXTENDABLE_STATE_LIST = ResourceKey.createRegistryKey(Zenith.loc("extendable_state_list"));
        public static final ResourceKey<Registry<ExtendableStateList.Entry>> EXTENDABLE_STATE_LIST_ENTRY = ResourceKey.createRegistryKey(Zenith.loc("state_list_entry"));
    }


}


