package net.zepalesque.zenith.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.zepalesque.zenith.Zenith;

import java.nio.file.Files;

// See https://github.com/TelepathicGrunt/Bumblezone/blob/6bbcb628672a77cfa7a2648be9b4d2428d1eeeb7/neoforge/src/main/java/com/telepathicgrunt/the_bumblezone/configs/neoforge/BzConfigHandler.java#L26
public class ZConfigHandler {

    public static void setup(IEventBus modEventBus) {

        try {
            Files.createDirectories(FMLPaths.CONFIGDIR.get().resolve(Zenith.MODID));
            createAndLoadConfigs(ModConfig.Type.CLIENT, ZConfig.CLIENT_SPEC, Zenith.MODID + "/client.toml");
            createAndLoadConfigs(ModConfig.Type.COMMON, ZConfig.COMMON_SPEC, Zenith.MODID + "/common.toml");
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to create Zenith config files: ", e);
        }
    }

    private static void createAndLoadConfigs(ModConfig.Type type, ModConfigSpec spec, String path) {
        ModLoadingContext.get().registerConfig(type, spec, path);

        final CommentedFileConfig configData = CommentedFileConfig.builder(FMLPaths.CONFIGDIR.get().resolve(path))
                .preserveInsertionOrder()
                .autoreload()
                .writingMode(WritingMode.REPLACE)
                .sync()
                .build();

        configData.load();
        spec.setConfig(configData);
    }
}
