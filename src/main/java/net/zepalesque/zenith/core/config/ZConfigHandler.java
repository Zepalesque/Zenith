package net.zepalesque.zenith.core.config;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLPaths;
import net.zepalesque.zenith.core.Zenith;

import java.nio.file.Files;

// See https://github.com/TelepathicGrunt/Bumblezone/blob/1.21-Arch/neoforge/src/main/java/com/telepathicgrunt/the_bumblezone/configs/neoforge/BzConfigHandler.java
public class ZConfigHandler {

    public static void setup(ModContainer mod, IEventBus bus) {

        try {
            Files.createDirectories(FMLPaths.CONFIGDIR.get().resolve(Zenith.MODID));
            mod.registerConfig(ModConfig.Type.CLIENT, ZConfig.CLIENT_SPEC, Zenith.MODID + "/client.toml");
            mod.registerConfig(ModConfig.Type.COMMON, ZConfig.COMMON_SPEC, Zenith.MODID + "/common.toml");
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to create Zenith config files: ", e);
        }
    }
}
