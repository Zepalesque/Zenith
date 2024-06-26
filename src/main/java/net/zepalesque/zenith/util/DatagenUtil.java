package net.zepalesque.zenith.util;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.codehaus.plexus.util.StringUtils;

import java.util.function.Supplier;

public class DatagenUtil {

    public static <T> ResourceLocation getId(Supplier<? extends T> object, Registry<T> registry) {
        return registry.getKey(object.get());
    }

    public static String getNameLocalized(String id) {
        return StringUtils.capitaliseAllWords(id.replace('_', ' '));
    }

    public static <T> String getNameLocalized(Supplier<? extends T> object, Registry<T> registry) {
        return getNameLocalized(getId(object, registry).getPath());
    }

    public static <T> String getNameLocalized(DeferredHolder<?, ?> holder) {
        return getNameLocalized(holder.getId().getPath());
    }

    public static boolean isVowel(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }

    public static String subtitleFor(SoundEvent sound) {
        return "subtitles." + sound.getLocation().getNamespace() + "." + sound.getLocation().getPath();
    }
}
