package net.zepalesque.zenith.util;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.codehaus.plexus.util.StringUtils;

import java.util.function.Supplier;

public class DatagenUtil {

    public static <T> ResourceLocation getId(Supplier<? extends T> object, Registry<T> registry) {
        return registry.getKey(object.get());
    }

    public static String getNameLocalized(String id) {
        return StringUtils.capitalise(id.replace('_', ' '));
    }

    public static <T> String getNameLocalized(Supplier<? extends T> object, Registry<T> registry) {
        return getNameLocalized(getId(object, registry).getPath());
    }

    public static <T> String getNameLocalized(DeferredHolder<?, ?> holder) {
        return getNameLocalized(holder.getId().getPath());
    }
}
