package net.zepalesque.zenith.util.data;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.codehaus.plexus.util.StringUtils;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * Some utilities to simplify certain processes.
 */
@SuppressWarnings("unused")
public class DatagenUtil {

    /**
     * Gets the registry ID of a certain object.
     * @param object The object, or a {@link Supplier} that supplies it.
     * @param registry The relevant {@link Registry}.
     * @return The object's registry ID in {@link ResourceLocation} form, if it exists.
     */
    @Nullable
    public static <T> ResourceLocation getId(Supplier<? extends T> object, Registry<T> registry) {
        return registry.getKey(object.get());
    }

    /**
     * <p>Localizes a {@link String}-based representation of some object's ID that uses <a href="https://en.wikipedia.org/wiki/Snake_case">snake_case naming/formatting convention.</a></p>
     * <p>Note that this will capitalize the first letter of each word.</p>
     * @param id The {@link String}.
     * @return The <abbr title = "In this case, this will res">localized</abbr>
     */
    public static String localize(String id) {
        return StringUtils.capitaliseAllWords(id.replace('_', ' '));
    }

    @Nullable
    public static <T> String localize(@Nullable ResourceLocation id) {
        return id == null ? null : localize(id.getPath());
    }

    @Nullable
    public static <T> String localize(Supplier<? extends T> object, Registry<T> registry) {
        @Nullable ResourceLocation id = getId(object, registry);
        return localize(id);
    }

    public static <T> String localize(DeferredHolder<?, ?> holder) {
        return localize(holder.getId().getPath());
    }

    /**
     * Generates a {@link String} subtitle localization for a given {@link SoundEvent}.
     * @param sound The relevant {@link SoundEvent}.
     * @return A subtitle string for the sound.
     */
    public static String subtitleFor(SoundEvent sound) {
        return "subtitles." + sound.getLocation().getNamespace() + "." + sound.getLocation().getPath();
    }

    /**
     * Self-explanatory.
     * @param c a {@link Character}.
     * @return Whether the given {@link Character} is a vowel, not including Y.
     */
    public static boolean isVowel(char c) {
        return isVowel(c, false);
    }

    /**
     * Self-explanatory.
     * @param c a {@link Character}.
     * @param includeY whether or not Y should be included as a vowel.
     * @return Whether the given {@link Character} is a vowel.
     */
    public static boolean isVowel(char c, boolean includeY) {
        return includeY ? existsIn(c, "aeiouyAEIOUY") : existsIn(c, "aeiouAEIOU");
    }

    private static boolean existsIn(char c, final String string) {
        return string.indexOf(c) != -1;
    }

}
