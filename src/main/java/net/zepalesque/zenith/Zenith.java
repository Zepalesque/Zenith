package net.zepalesque.zenith;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
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
import net.zepalesque.zenith.api.condition.ConditionElement;
import net.zepalesque.zenith.api.condition.ConditionElements;
import net.zepalesque.zenith.api.condition.ConfigCondition;
import net.zepalesque.zenith.api.condition.config.ConfigSerializer;
import net.zepalesque.zenith.config.ZConfig;
import org.slf4j.Logger;

import java.nio.file.Path;

@Mod(Zenith.MODID)
public class Zenith
{
    public static final String MODID = "zenith";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static final Path DIRECTORY = FMLPaths.CONFIGDIR.get().resolve(Zenith.MODID);

    public Zenith(IEventBus bus, Dist dist) {

        bus.addListener(this::commonSetup);
        ConditionElements.ELEMENTS.register(bus);

        // Register example config serializer
        ConfigCondition.registerSerializer("zenith", new ConfigSerializer(ZConfig.Serializer::serialize, ZConfig.Serializer::deserialize));

        if (DIRECTORY.toFile().mkdirs())
            LOGGER.info("Created folder for Zenith config");

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ZConfig.COMMON_SPEC, MODID + "/common.toml");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

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
        public static final ResourceKey<Registry<Codec<? extends ConditionElement<?>>>> CONDITION_ELEMENT = ResourceKey.createRegistryKey(Zenith.loc("condition_serializer"));

    }


}


