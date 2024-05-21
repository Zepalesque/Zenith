package net.zepalesque.zenith.util;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.codehaus.plexus.util.StringUtils;

import java.util.function.Supplier;

public class DataGenUtil {

    public static <T> ResourceLocation getId(Supplier<? extends T> object, Registry<T> registry) {
        return registry.getKey(object.get());
    }

    public static String getNameLocalized(String id) {
        return StringUtils.capitalise(id.replace('_', ' '));
    }

    public static <T> String getNameLocalized(Supplier<? extends T> object, Registry<T> registry) {
        return getNameLocalized(getId(object, registry).getPath());
    }
}
