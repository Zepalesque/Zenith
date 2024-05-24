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
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;
import net.zepalesque.zenith.api.biometint.BiomeTint;
import net.zepalesque.zenith.api.biometint.BiomeTints;
import net.zepalesque.zenith.api.condition.Condition;
import net.zepalesque.zenith.api.condition.ConditionElements;
import net.zepalesque.zenith.api.condition.ConfigCondition;
import net.zepalesque.zenith.api.condition.config.ConfigSerializer;
import net.zepalesque.zenith.config.ZConfig;
import net.zepalesque.zenith.data.generator.ZenithDataMapGen;
import net.zepalesque.zenith.data.generator.ZenithRegistrySets;
import net.zepalesque.zenith.loot.condition.ZenithLootConditions;
import net.zepalesque.zenith.recipe.condition.ZenithRecipeConditions;
import net.zepalesque.zenith.world.feature.gen.ZenithFeatures;
import net.zepalesque.zenith.world.feature.placement.ZenithPlacementModifiers;
import net.zepalesque.zenith.world.state.ZenithStateProviders;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

// TODO: More documentation
@Mod(Zenith.MODID)
public class Zenith {
    public static final String MODID = "zenith";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static final Path DIRECTORY = FMLPaths.CONFIGDIR.get().resolve(Zenith.MODID);

    public Zenith(IEventBus bus, Dist dist) {

        bus.addListener(this::commonSetup);
        bus.addListener(this::dataSetup);
        bus.addListener(this::registerDataMaps);
        bus.addListener(this::registerRegistries);
        bus.addListener(DataPackRegistryEvent.NewRegistry.class, event -> event.dataPackRegistry(Keys.CONDITION, Condition.ELEMENT_CODEC, Condition.ELEMENT_CODEC));

        ConditionElements.ELEMENTS.register(bus);
        BiomeTints.TINTS.register(bus);
        ZenithRecipeConditions.CODECS.register(bus);
        ZenithPlacementModifiers.FILTERS.register(bus);
        ZenithLootConditions.LOOT_CONDITIONS.register(bus);
        ZenithStateProviders.PROVIDERS.register(bus);
        ZenithFeatures.FEATURES.register(bus);

        // Register example config serializer
        ConfigCondition.registerSerializer("zenith", new ConfigSerializer(ZConfig.Serializer::serialize, ZConfig.Serializer::deserialize));

        if (DIRECTORY.toFile().mkdirs())
            LOGGER.info("Created folder for Zenith config");

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ZConfig.COMMON_SPEC, MODID + "/common.toml");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

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


