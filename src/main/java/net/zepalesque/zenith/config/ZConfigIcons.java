package net.zepalesque.zenith.config;

import com.google.common.collect.ImmutableMap;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.loading.FMLConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.zepalesque.zenith.Zenith;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ZConfigIcons {

    private static Map<ModConfigSpec.ConfigValue<?>, ResourceLocation> ICONS = new HashMap<>();
    private static boolean frozen = false;

    public static <T> ModConfigSpec.ConfigValue<T> register(ModConfigSpec.ConfigValue<T> value, ResourceLocation iconLoc) {
        ICONS.put(value, iconLoc);
        return value;
    }

    public static Map<ModConfigSpec.ConfigValue<?>, ResourceLocation> freeze() {
        if (frozen) {
            Zenith.LOGGER.warn("Attempted to freeze config icon map, but it has already been frozen!");
            return ICONS;
        } else {
            ImmutableMap<ModConfigSpec.ConfigValue<?>, ResourceLocation> immutable = ImmutableMap.copyOf(ICONS);
            Map<ModConfigSpec.ConfigValue<?>, ResourceLocation> old = ICONS;
            ICONS = immutable;
            old.clear();
            frozen = true;
            return immutable;
        }
    }

    @Nullable
    public static ResourceLocation iconFor(ModConfigSpec.ConfigValue<?> value) {
        return ICONS.get(value);
    }
}
